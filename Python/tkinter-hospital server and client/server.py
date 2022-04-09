import socket, pickle
import tkinter as tk
from tkinter import *
from tkinter import ttk
from tkinter import messagebox
from tkinter.messagebox import askyesno
from datetime import datetime
from collections import deque
import threading




#start gui
root = Tk()

#creating patient object
class Patient:
    def __init__(self,department, name, birth, social, symptom, prescription, time, revisit, record):
        self.department = department
        self.name = name
        
        self.birth = birth
        self.social = social
        self.symptom = symptom
        self.prescription = prescription
        self.time = time
        self.revisit = revisit
        self.record = record
    #this part stores previous diagnosis record
    def add_record(self, key, value):
        self.record[key]=value
            
#create patient_list object
class patient_list(dict):
    def __init__(self):
        self = dict()
    def add(self, key, value):
        self[key]=value
        
#initialize patient list
patient_list = patient_list()



#function to make easier to check patient is first visit
def check_revisit(pat_ssn):
    if pat_ssn in patient_list:
        return True
    else:
        return False
    
#this function parse the patient data from client and stores in patient_list
def save_patient(patient):
    patient_info = patient.split("\n")
    #remove last value which is empty space
    patient_info.pop(-1)
    depart = patient_info[0]
    name = patient_info[1]
    birth = patient_info[2]
    social = patient_info[3]
    symptom = patient_info[4]
    presc = patient_info[5]
    time = int(datetime.now().strftime('%Y%m%d%H%M%S'))
    revisit = check_revisit(social)

    #check first time visit or not  
    if not revisit:
        print("revisit")
        new_pat = Patient(depart, name, birth ,social ,symptom, presc, time ,revisit, dict())
        patient_list.add(new_pat.social, new_pat)
        new_pat.record[new_pat.time] = new_pat
    else:
        pat = patient_list[social]
        new_pat = Patient(depart, name, birth ,social ,symptom, presc, time ,revisit, pat.record)
        pat.revisit = True
        patient_list[social].add_record(time, new_pat)
    print(patient_list[social].record)
    

#create a socket to interact with client
soc = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

print('Socket Created')

soc.bind(('localhost', 8888))

soc.listen(3)

print('Waiting for connection')

#listbox for patient list
pat_list = Listbox(root, height=42, width=35, state='normal', selectmode = SINGLE)
pat_list.grid(sticky='w', column = 0, row = 1, rowspan=100)

#format patient information
def format(patient):
    print(patient)
    depart = "Department: " + patient.department + "\n"
    name = "Name: "+ patient.name + "\n"
    birth = "Date of Birth: " + patient.birth + "\n"
    social = "SSN: " + patient.social + "\n"
    symptom = "Symptoms: " + patient.symptom + "\n"
    pre = "Prescription: "+patient.prescription+ "\n"
    time = datetime.strptime(str(patient.time),"%Y%m%d%H%M%S").strftime("%B %d, %Y %H:%M:%S") + "\n\n"
    revisit = "Revisit (T/F): " + str(patient.revisit)
    return time + depart + name + birth + social + symptom + pre + revisit

#update the contents in the widgets
def update(word):
    pat_list.delete(0, END)
    if not word and pat_list:
        for i in patient_list:
            pat_list.insert(END, patient_list[i].name + " (" +patient_list[i].social + ")")
    elif word and pat_list:
        for social,pat in patient_list.items():
            if word in social or word in pat.name:
                pat_list.insert(END, pat.name + " (" + social + ")")

#receive data from client
def receive():
    while True:
        c, addr = soc.accept()
        print("Connected with ", addr)
        data = c.recv(10000)
        patient = pickle.loads(data)
        c.close()
        save_patient(patient)
        update("")

#function to show infomation of patient in messagebox
def showinfo(event,patient):
    select = event.widget.curselection()
    info = format(patient.get(list(patient)[select[0]]))
    messagebox.showinfo("Patient Information", info)
    
#function to show information of patient that is clicked by user 
def record_pat(msgbox, pat):
    patient = patient_list[pat]
    if patient.record:
        record = patient.record
        t = 0
        window = Toplevel()
        window.title("Patient's record")
        window.geometry('400x400')
        window.resizable(False,False)
        #listbox for patient's record
        listbox = Listbox(window, height = 27, width = 100, state='normal', selectmode = SINGLE)
        listbox.grid(sticky='ewsn', column = 0, row=1)
        for i in record:
            t = i
            time = datetime.strptime(str(t),"%Y%m%d%H%M%S").strftime("%B %d, %Y %H:%M:%S")
            listbox.insert("end", time)

        listbox.bind('<Double-1>', lambda event: showinfo(event, record))
        window.mainloop()
    else:
        messagebox.showinfo("Warning", "No previous record")

    
#function to delete patient from the list
def delete_pat(msgbox, pat):
    confirm = askyesno("Confirmation", "Are you sure to delete this patient?")
    if confirm:
        del patient_list[pat]
        update(pat_list)
        msgbox.destroy()
    
#function to manipulate user's action on patient waiting list 
def selection(event):
    select = event.widget.curselection()
    cur = list(patient_list)
    patient = cur[select[0]]
    print(cur[select[0]])
    
    if select:
        msgbox = Toplevel(root)
        msgbox.title("Patient's information")
        msgbox.geometry('400x400')
        msgbox.resizable(height=False, width = False)
        msgbox_txt = Text(msgbox, bd=1.5, width = 56)
        msgbox_txt.insert(END, format(patient_list[cur[select[0]]]))
        msgbox_txt.configure(state='disable')
        msgbox_txt.grid(row=0, column=0, columnspan = 3)

        
        #buttons
        record = Button(msgbox, text = "Record", height = 5, relief = "ridge", command = lambda:record_pat(msgbox,patient)).grid(row=1, column=0, sticky = 'nesw')
        delete = Button(msgbox, text = "Delete",relief = "ridge", command=lambda:delete_pat(msgbox, patient)).grid(row =1 , column = 1, sticky = 'nesw')
        cancel = Button(msgbox, text = "Cancel",relief = "ridge", command= lambda:msgbox.destroy()).grid(row =1 , column = 2, sticky = 'nesw')
        
        msgbox.mainloop()
    else:
        messagebox.showinfo("Warning", "Please select any patient in the list")


#thread to prevent tkinter mainloop blocking socket listening
thread_2 = threading.Thread(target=receive)
thread_2.daemon = True
thread_2.start()

root.title("JWP Hospital server")
root.resizable(False, False)
root.geometry('250x500')

find = Entry(root, width = 29,bd = 1.5)
find.grid(sticky = 'w',column=0, row=0)
find_button = Button(root, text="Find", width = 5, bd=1.5, relief = "ridge", command = lambda:update(find.get())).grid(sticky='e', column =0 , row= 0,  pady = 1)

pat_list.bind('<Double-1>', selection)
root.mainloop()
