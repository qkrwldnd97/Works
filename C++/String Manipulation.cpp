
#include <stdio.h>
#include "MyString.h"


int MyString::slength(const char *s) const
{
    int len = 0;

    while(s[len] != '\0'){
        len++;
    }

  return len;
}

void MyString::initialize(const char * s)
{
   int len = slength(s);
   _s = new char[len + 1];

    int i = 0;
    while(s[i] != '\0'){
        _s[i] = s[i];
        i++;
    }
    
    
}

MyString::MyString(const char * s)
{
  initialize(s);
}


MyString::MyString(const MyString &s)
{
  initialize(s._s);
}


MyString::MyString()
{
  _s = new char[1];
  *_s = 0;
}


MyString & MyString::operator = (const MyString & other) {
  if (this != &other) 
  {
    // deallocate old memory
    delete [] _s;

    // Initialize _s with the "other" object.
    initialize(other._s);

    // by convention, always return *this
    return *this;
  }
}


MyString MyString::substring(int i, int n)
{

  // Allocate memory for substring
  // Copy characters of substring
    char * temp = new char[n];
    if (i>slength(_s)) {
        return *(new MyString());
    }
    int a=0;
    for (; _s[i]!='\0';i++ ) {
        temp[a]=_s[i];
        a++;
        if (a==n) {
            break;
        }
    }
    temp[a]='\0';
    
    return *(new MyString(temp));
    
}

// Remove at most n chars starting at location i
void
MyString::remove(int i, int n)
{
  // If i is beyond the end of string return
  // If i+n is beyond the end trunc string
  // Remove characters
    if (i > length()){
        return;
    }
    else if ( (i + n) > length()){
        return;
    }
    
    int k = 0;
    int j = 0;
    
    for (k = 0; k < n; k++){
    
        for (j = i; j < length() - 1; j++){
            _s[j] = _s[j + 1];
        }
        
    }
    
    _s[length() - n] = '\0';
    
    return;
    
    
}

// Return true if strings "this" and s are equal
bool
MyString::operator == (const MyString & s){
    int i = 0;
    bool result;
    
    while( (_s[i] != '\0' && s._s[i] != '\0') && (_s[i] == s._s[i]) ){
        i++;
    }
    
    if( (_s[i] - s._s[i]) == 0 ){
        return true;
    }
    else{
        return false;
    }
    
}


// Return true if strings "this" and s are not equal
bool MyString::operator != (const MyString &s){
    int i = 0;
    
    while( (_s[i] != '\0' && s._s[i] != '\0') && (_s[i] == s._s[i]) ){
        i++;
    }
    
    if( (_s[i] - s._s[i]) != 0 ){
        return true;
    }
    else{
        return false;
    }
}

// Return true if string "this" and s is less or equal
bool MyString::operator <= (const MyString &s){
    int i = 0;
    
    while ((_s[i] != '\0' && s._s[i] != '\0') && (_s[i] == s._s[i])){
        i++;
    }
    
    if( (_s[i] - s._s[i]) <= 0 ){
        return true;
    }
    else{
        return false;
    }
    
}

// Return true if string "this" is greater than s
bool MyString::operator > (const MyString &s){
    int i = 0;
    
    while( (_s[i] != '\0' && s._s[i] != '\0') && (_s[i] == s._s[i]) ){
        i++;
    }
    
    if( (_s[i] - s._s[i]) > 0 ){
        return true;
    }
    else{
        return false;
    }
}

// Return character at position i.  Return '\0' if out of bounds.
char MyString::operator [] (int i){
    if(i > length()){
        return '\0';
    }
    
    return _s[i];
}

// Return C representation of string
const char *
MyString::cStr()
{

  return _s;
}

int
MyString::length() const
{

  return slength(_s);
}

// Concatanate two strings (non member method)
MyString operator + (const MyString &s1, const MyString &s2){
    int i = 0;
    int k = 0;
    int len = 0;
    len = s1.length() + s2.length() + 1;
   
    MyString s;
    delete s._s;
    s._s = new char[len];
    
    for(i = 0; i < s1.length(); i++){
        s._s[i] = s1._s[i];
    }
    for(i = s1.length(); i < len; i++){
        s._s[i] = s2._s[k];
        k++;
    }
    
    s._s[i] = '\0';
    
    return s;
}













