package DataStructures;

/** Source code example for "A Practical Introduction to Data
    Structures and Algorithm Analysis, 3rd Edition (Java)" 
    by Clifford A. Shaffer
    Copyright 2008-2011 by Clifford A. Shaffer
*/

/** Linked list implementation */
public class LList<E> implements List<E> {
private Link<E> head;         // Pointer to list header
private Link<E> tail;         // Pointer to last element
protected Link<E> curr;       // Access to current element
int cnt;		      // Size of list

/** Constructors */
public LList(int size) { this(); }   // Constructor -- Ignore size
public LList() {
  curr = tail = head = new Link<E>(null); // Create header
  cnt = 0;
}

/** Remove all elements */
public void clear() {
  head.setNext(null);         // Drop access to links
  curr = tail = head = new Link<E>(null); // Create header
  cnt = 0;
}

/** Insert "it" at current position */
public void insert(E it) {
  curr.setNext(new Link<E>(it, curr.next()));  
  if (tail == curr) tail = curr.next();  // New tail
  cnt++;
}

/** Append "it" to list */
public void append(E it) {
  tail = tail.setNext(new Link<E>(it, null));
  cnt++;
}

/** Remove and return current element */
public E remove() {
  if (curr.next() == null) return null; // Nothing to remove
  E it = curr.next().element();         // Remember value
  if (tail == curr.next()) tail = curr; // Removed last
  curr.setNext(curr.next().next());     // Remove from list
  cnt--;				// Decrement count
  return it;                            // Return value
}

/** Set curr at list start */
public void moveToStart()
{ curr = head; }
/** Set curr at list end */
public void moveToEnd()
{ curr = tail; }

/** Move curr one step left; no change if now at front */
public void prev() {
  if (curr == head) return; // No previous element
  Link<E> temp = head;
  // March down list until we find the previous element
  while (temp.next() != curr) temp = temp.next();
  curr = temp;
}

/** Move curr one step right; no change if now at end */
public void next()
  { if (curr != tail) curr = curr.next(); }

/** @return List length */
public int length() { return cnt; }

/** @return The position of the current element */
public int currPos() {
  Link<E> temp = head;
  int i;
  for (i=0; curr != temp; i++)
    temp = temp.next();
  return i;
}

/** Move down list to "pos" position */
public void moveToPos(int pos) {
  assert (pos>=0) && (pos<=cnt) : "Position out of range";
  curr = head;
  for(int i=0; i<pos; i++) curr = curr.next();
}

/** @return Current element value */
public E getValue() {
  if(curr.next() == null) return null;
  return curr.next().element();
}
// Extra stuff not printed in the book.

  /**
   * Generate a human-readable representation of this list's contents
   * that looks something like this: < 1 2 3 | 4 5 6 >.  The vertical
   * bar represents the current location of the fence.  This method
   * uses toString() on the individual elements.
   * @return The string representation of this list
   */
  public String toString()
  {
    // Save the current position of the list
    int oldPos = currPos();
    int length = length();
    StringBuffer out = new StringBuffer((length() + 1) * 4);

    moveToStart();
    out.append("< ");
    for (int i = 0; i < oldPos; i++) {
      out.append(getValue());
      out.append(" ");
      next();
    }
    out.append("| ");
    for (int i = oldPos; i < length; i++) {
      out.append(getValue());
      out.append(" ");
      next();
    }
    out.append(">");
    moveToPos(oldPos); // Reset the fence to its original position
    return out.toString();
  }
  public void reverse(){
	  if(cnt <= 1){ //null messes up Ttemp
		  return;
	  }
	  
	  Link<E> Htemp = new Link<E>(null); //temporary head
	  Link<E> Ttemp = head.next(); //new tail
	  Link<E> t; 
	  curr = head;
	  //Loops through list and inserts the nodes right after head which makes the list backwards
	  while(curr != tail){ //loops through list
		  t = new Link<E>(curr.next().element(), Htemp.next()); //clone Link
		  Htemp.setNext(t); //insert clone
		  curr = curr.next();
		  
	  }
	  //set LList to the reverse
	  curr = Htemp; 
	  head = Htemp; 
	  tail = Ttemp;
	  tail.setNext(null);
	  
	  
	  //can be done without using as much memory
  }
  public void removeEveryOther(){
	  moveToStart();
	  while(remove() != null){
		 next();
	  }
  }
  public void doubleEnd(){
	  //double beginning and end
	  //I think using written out methods is better because I don't have to think about cnt
	  if(cnt < 2){
		  return;
	  }
	  head.setNext(new Link<E>(head.next().element(), head.next()));
	  cnt++;
	  tail = tail.setNext(new Link<E>(tail.element(), null));
	  cnt++;
  }
  public void doubleEnd1(){
	  //Jenny's Method for double end and the classes' version
	  if(cnt<2){
		  return;
	  }
	  curr = head;
	  insert(head.next().element());
	  append(tail.element());
  }
  public E removeNth(int n){
	  if(n>cnt-1){
		  return null;
	  }
	  curr = head;
	  for(int i = 0; i < n; i++){
		  curr = curr.next();
	  }
	  Link<E> rem = curr.next();
	  if(curr.next() == tail){
		  tail = curr;
	  }
	  curr.setNext(curr.next().next());
	  cnt--;
	  return rem.element();
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  public void swap(){
	  //Swaps the two values after curr
	  Link<E> a;
	  Link<E> b;
	  if(curr.next() == null || curr.next().next() == null){ return; } //Checks if there are two values to the right
	  a = curr.next();
	  b = curr.next().next();
	  //swaps
	  curr.setNext(b);
	  a.setNext(b.next());
	  b.setNext(a);
	  //changes tail
	  if(b == tail){
		  tail = a;
	  }
  }
  public E removeByValue(E toRemove){
	  //removes value toRemove
	  curr = head;
	  while(curr != tail){
		  if(curr.next().element().equals(toRemove)){ //checks for the value
			  return remove();
		  }
		  next();
	  }
	  return null; //no value could be found
  }
}