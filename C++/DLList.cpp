#include "DLList.h"
#include<cstdio>
#include<stdio.h>
#include<iostream>


DLList::DLList()
{
	head = new DLNode();
	head->next = head;
	head->prev = head;
}


void DLList::print()
{
	DLNode *e = head -> next;
	while(head != e){
		printf("%d\n", e->data);
		e = e->next;
	}
}

void DLList::printSorted()
{
	int i = 0;
    int j = 0;
    int temp = 0;
    int listData[numElement()] = {0,};
    
    DLNode *nodePT = head->next;
    
    for(i = 0; i < numElement(); i++){
        listData[i] = nodePT->data;
        nodePT = nodePT->next;
    }
    
    for(i = 0; i < numElement(); i++){ // for(1)
        
        for(j = 0; j < (numElement() - 1); j++){ // for(1-1)
            
            if(listData[i] < listData[j]){
                temp = listData[i];
                listData[i] = listData[j];
                listData[j] = temp;
            }
            
        }// end of for(1-1)
        
    }// end of for(1)
    
    
    for(i = 0; i < numElement(); i++){
        printf("%d\n",listData[i]);
    }
}

void DLList::insertFront(int data)
{
	 DLNode * e = new DLNode();
    e->data = data;
    e->next = head->next;
    e->prev = head;
    e->next->prev = e;
    e->prev->next = e;
}

bool DLList::removeLast(int & data)
{
	DLNode *nodeFront = head->next;
    DLNode *nodeBack;
    
    while(nodeFront->next != head){
        nodeBack = nodeFront;
        nodeFront = nodeFront->next;
    }
    
    data = nodeFront->data;
    
    nodeBack->next = nodeFront->next;
    nodeBack->next->prev = nodeFront->prev;
    delete(nodeFront);
    
    return true;
}

DLList * DLList::difference(DLList & list)
{
	DLList * diff = new DLList();
	bool flag = true;
	DLNode *nodePT1 = head ->next;
	DLNode *nodePT2 = list.head->next;
	while(nodePT1 != head){
		while(nodePT2 != list.head){
			if(nodePT1->data == nodePT2->data){
				flag = false;
				break;
			}
			nodePT2 = nodePT2->next;
		}//while(2)
		if(flag){
			DLNode * e = new DLNode();
			e->data = nodePT1->data;
			e->next = diff->head->next;
            e->prev = diff->head;
            e->next->prev = e;
            e->prev->next = e;
		}
		flag = true;
		nodePT1 = nodePT1->next;
		nodePT2 = list.head->next;
	}
	return diff;
}


DLList * DLList::intersection(DLList & list)
{
	DLList * inter = new DLList();
	/** @todo intersection **/
	DLNode *nodePT1 = head->next;
    DLNode *nodePT2 = list.head->next;
    
    while(nodePT1 != head){ 
        
        while(nodePT2 != list.head){ 
            
            if(nodePT1->data == nodePT2->data){
                DLNode * e = new DLNode();
                e->data =  nodePT2->data;
                //Add at the beginning
                e->next = inter->head;
                e->prev = inter->head->prev;
                e->prev->next = e;
                inter->head->prev = e;
            }
            
            nodePT2 = nodePT2->next;
        }
        
        nodePT1 = nodePT1->next;
        nodePT2 = list.head->next;
        }
	return  inter;
}

