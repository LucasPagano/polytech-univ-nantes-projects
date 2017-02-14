class Item :
    def __init__(self,previous,next,value):
        self.previous=previous
        self.next=next
        self.value=value
        if self.previous:
            self.previous.next=self
        if self.next:
            self.next.previous=self
        

class List:
    def __init__(self,head):
        self.head=head
        
    def insert(self,value,position):
        if position==0:
            item=Item(None,self.head,value)
            self.head.previous=item
            self.head=item       
        else:
            actualItem=self.head
            for i in range(1,position):
                actualItem=actualItem.next
            item=Item(actualItem,actualItem.next,value)
            actualItem.next=item
            item.next.previous=item
    def __str__(self):
        actualItem=self.head
        string="["+str(actualItem.value)
        while actualItem.next != None:
            actualItem=actualItem.next
            string+=","+str(actualItem.value)
        string=string+"]"
        return string
    def concat(self,liste2):
        actualItem=self.head
        while actualItem.next != None:
            actualItem=actualItem.next
        liste2.head.previous=actualItem
        actualItem.next=liste2.head
    

i1=Item(None,None,1)
i2=Item(i1,None,2)
i3=Item(i2,None,3)
i4=Item(i3,None,4)

t=List(i1)

j1=Item(None,None,5)
j2=Item(j1,None,6)

j1.next=j2
y = List(j1)

t.concat(y)
print(t)







    
        
