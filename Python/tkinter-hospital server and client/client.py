import socket
import pickle

import tkinter as tk
from tkinter import *
from tkinter import ttk
from tkinter import messagebox

#creating patient object
class Patient:
    def __init__(self, department, namespace, birth, social, symptom):
        self.department = department.get()
        self.name = namespace.get()
        self.birth = birth.get()
        self.social= social.get()
        self.symptom = symptom.get("1.0", END)

#creating patient list object
class patient_list(dict):
    def __init__(self):
        self = dict()
    def add(self, key, value):
        self[key] = value

#function helps parse patient object to string
def format(patient):
    return "Department: " + patient.department + "\nName: " + patient.name + "\nDate of birth: " + patient.birth + "\nSSN: " + patient.social + "\nSymptoms: " + patient.symptom

#function to format patient's information when sending to server
def data_format(patient, prescription):
    return patient.department + "\n" + patient.name + "\n" + patient.birth + "\n" + patient.social + "\n" + patient.symptom + prescription

#function to show information of patient thru messagebox
def showinfo(title,string):
    messagebox.showinfo(title, string)

#function to create popup that enables user to enter prescription
def prescribe_popup(msgbox, patient):
    prescription = Toplevel(msgbox)
    prescription.title("Prescription")
    prescription.geometry("500x700")
    prescription.resizable(False,False)
    label = Label(prescription,text=patient.name+"'s prescription", font = ('Helvetica', 15, 'bold')).pack()
    context = Text(prescription, bd= 1.5, relief = "ridge")
    context.pack(expand = True, fill = "both", pady=(10,60))
    btn = Button(prescription,text = "Submit", height = 2, width = 10, relief = "groove", command = lambda:sendtoserver(patient, context.get("1.0", END)))
    btn.place(x = 210, y = 650)
    
#function to create two buttons 'prescription' and 'cancel'
def prescribe(msgbox, patient):
    pre_button = Button(msgbox, text = "Prescribe", command = lambda:prescribe_popup(msgbox, patient))
    pre_button.place(x = 12, y = 363, height=30, width = 70)
    cancel_button = Button(msgbox, text = 'Cancel', command = lambda:msgbox.destroy())
    cancel_button.place(x=320, y = 363, height=30, width = 70)
    
#function to send data to server
def sendtoserver(patient, prescription):
    c = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        c.connect(('localhost', 8888))
        data = pickle.dumps(data_format(patient, prescription))
        #print (data)
        c.sendall(data)
        c.close()
        messagebox.showinfo("Success", "Successfully sent to server")
    except:
        messagebox.showinfo("Error", "Failed connecting to the server.")

    

root = Tk()
root.title("JWP Hospital")
root.resizable(False,False)
#set size of the panel
root.geometry('500x700')

#init list of patient
patient_obj = patient_list()


OptionList = [
    "Please Select the Department",
    "Internal Medicine",
    "Orthopedics",
    "Pediatrics",
    "Dermatology Clinic",
    "Obstetrics gynecology"
    ]


#creating listbox with label
data_label = ttk.Label(text = "Current waiting list", font=('Helvetica', 12, 'bold')).grid(column = 0 , row=0)
data = Listbox(root, height=42, width=35, state = 'normal', selectmode = SINGLE)
data_grid= data.grid(sticky='w',column = 0, row = 1,rowspan = 100)

#creating scroll bar
scroll = tk.Scrollbar(root, orient = "vertical", command=data.yview)
scroll.grid()
data.config(yscrollcommand = scroll.set)


#initialize all widgets
option = StringVar()

department = ttk.Combobox(root,  textvariable = option, state="readonly", width = 20)
department['values'] = OptionList
department.current(0)
department.grid(row=1, column=1, ipadx = 44)
depart = ttk.Label(text = "Please select a department: ",font=('Helvetica', 9, 'bold'))
depart_grid = depart.grid(row = 0,column = 1)

name= ttk.Label(text = "\nPatient's name: ",font=('Helvetica', 9, 'bold')).grid(row=2, column = 1)
namespace = Entry(root, bd = 1.5)
namespace_grid = namespace.grid(row=3, column = 1, ipadx = 52)

dob = ttk.Label(text = "\nPatient's Date of Birth (Month/Day/Year): ",font=('Helvetica', 9, 'bold')).grid(row = 4,column=1)
birth = Entry(root, bd = 1.5)
birth_grid = birth.grid(row=5, column = 1, ipadx= 52)

ssn = ttk.Label(text = "\nPatient's Social Security Number (SSN): ",font=('Helvetica', 9, 'bold')).grid(row=6, column=1)
social = Entry(root, bd = 1.5)
social_grid = social.grid(row= 7, column= 1, ipadx= 52)

sym = ttk.Label(text = "\nPatient's Symptoms in detail: ",font=('Helvetica', 9, 'bold')).grid(row=8, column=1)
symptom = Text(root, width=35, height=30,bd=1.5)
symptom_grid = symptom.grid(row=9, column=1)

#clear entry blocks
def clear():
    department.current(0)
    namespace.delete(0, END)
    birth.delete(0, END)
    social.delete(0, END)
    symptom.delete(1.0,END)

def patient(data, department, namespace, birth, social, symptom):
    data.configure(state='normal')
    data.delete(0, 'end')
    pat = Patient(department, namespace, birth, social, symptom)
    
    #adding patient to a list & check whether there are any empty blanks
    if len(pat.social) == 0 or len(pat.name) == 0 or len(pat.birth) == 0 or len(pat.symptom) == 0:
        messagebox.showinfo("Alert","Please fill in patient's information.")
    else:
        patient_obj.add(pat.social, pat)
        #after successfully adding patient to the list clear the entry blocks
        clear()

    
    #this loops thru the list and update whenever button is clicked
    for i in patient_obj.values():
            data.insert(END, i.name)
    
    
def selection(event):
    select = event.widget.curselection()
    #way to index a dictionary (patient list)
    curpatient = list(patient_obj.values())

    #make sure user is selecting a patient in a list
    if select:
        msgbox = Toplevel(root)
        msgbox.title("Patient's information")
        msgbox.geometry('400x400')
        msgbox.resizable(height = False, width = False)
        msgbox_text = Text(msgbox, width=100, height=27, bd=1.5, wrap = WORD)
        msgbox_text.insert(END, format(curpatient[select[0]]))
        msgbox_text.configure(state='disable')
        msgbox_text.grid(row=0, column=0)
        #msgbox.grab_set()
        
        #adding prescribe function
        prescribe(msgbox, curpatient[select[0]])

        msgbox.mainloop()
    else:
        showinfo("Warning","Please select any patient in the list")

button= ttk.Button(root, text = "Submit", command= lambda: patient(data, department, namespace, birth,social,symptom))
button.place(x=250, y=655, height = 40, width = 250)



data.bind('<Double-1>', selection)

root.mainloop()
