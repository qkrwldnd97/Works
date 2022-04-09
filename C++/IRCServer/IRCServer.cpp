const char * usage =
"                                                               \n"
"IRCServer:                                                   \n"
"                                                               \n"
"Simple server program used to communicate multiple users       \n"
"                                                               \n"
"To use it in one window type:                                  \n"
"                                                               \n"
"   IRCServer <port>                                          \n"
"                                                               \n"
"Where 1024 < port < 65536.                                     \n"
"                                                               \n"
"In another window type:                                        \n"
"                                                               \n"
"   telnet <host> <port>                                        \n"
"                                                               \n"
"where <host> is the name of the machine where talk-server      \n"
"is running. <port> is the port number you used when you run    \n"
"daytime-server.                                                \n"
"                                                               \n";

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <time.h>


#include <iostream> // input / output
#include <fstream> //file  
#include <map>
#include <vector>
#include <string>
#include <stdexcept>      // std::out_of_range
#include "IRCServer.h"

using namespace std;

int QueueLength = 5;


/* USER */
map <string, string> userList;  // the whole user db
// 1st string : username, 
// 2nd string : password -> users["eric"] = "ericPW1"

/* ROOM */
//map < string, vector<string> > rooms; // first string : room name, vector : list of the users
// 1st string(key): room name, 2nd vector: (key : userlist, value : msglist)
//map < string, map<string, vector <string>* > *> roomList; // whole room db
map < string, map<string, vector <string> > > roomList; // whole room db

//test
int
IRCServer::open_server_socket(int port) {

// Set up the ip and port
struct sockaddr_in serverIPAddress; 
memset( &serverIPAddress, 0, sizeof(serverIPAddress) );
serverIPAddress.sin_family = AF_INET;
serverIPAddress.sin_addr.s_addr = INADDR_ANY;
serverIPAddress.sin_port = htons((u_short) port);
  
// Allocate a socket
int masterSocket =  socket(PF_INET, SOCK_STREAM, 0);
if ( masterSocket < 0) {
perror("socket");
exit( -1 );
}

// Set socket options to reuse port. Otherwise we will
// have to wait about 2 minutes before reusing the sae port number
int optval = 1; 
int err = setsockopt(masterSocket, SOL_SOCKET, SO_REUSEADDR, 
    (char *) &optval, sizeof( int ) );

// Bind the socket to the IP address and port
int error = bind( masterSocket,
 (struct sockaddr *)&serverIPAddress,
 sizeof(serverIPAddress) );
if ( error ) {
perror("bind");
exit( -1 );
}

// Put socket in listening mode and set the 
// size of the queue of unprocessed connections
error = listen( masterSocket, QueueLength);
if ( error ) {
perror("listen");
exit( -1 );
}

return masterSocket;
}

void
IRCServer::runServer(int port)
{
int masterSocket = open_server_socket(port);

initialize();

while ( 1 ) { //infinite loop to keep the server on running

// Accept incoming connections
struct sockaddr_in clientIPAddress;
int alen = sizeof( clientIPAddress );
int slaveSocket = accept( masterSocket,
 (struct sockaddr *)&clientIPAddress,
 (socklen_t*)&alen);

if ( slaveSocket < 0 ) {
perror( "accept" );
exit( -1 );
}

// Process request.
processRequest( slaveSocket );
}
}

int
main( int argc, char ** argv )
{
// Print usage if not enough arguments
if ( argc < 2 ) {
fprintf( stderr, "%s", usage );
exit( -1 );
}

// Get the port from the arguments
int port = atoi( argv[1] );

IRCServer ircServer;

// It will never return
ircServer.runServer(port);

}

//
// Commands:
//   Commands are started y the client.
//
//   Request: ADD-USER <USER> <PASSWD>\r\n
//   Answer: OK\r\n or DENIED\r\n
//
//   REQUEST: GET-ALL-USERS <USER> <PASSWD>\r\n
//   Answer: USER1\r\n
//            USER2\r\n
//            ...
//            \r\n
//
//   REQUEST: CREATE-ROOM <USER> <PASSWD> <ROOM>\r\n
//   Answer: OK\n or DENIED\r\n
//
//   Request: LIST-ROOMS <USER> <PASSWD>\r\n
//   Answer: room1\r\n
//           room2\r\n
//           ...
//           \r\n
//
//   Request: ENTER-ROOM <USER> <PASSWD> <ROOM>\r\n
//   Answer: OK\n or DENIED\r\n
//
//   Request: LEAVE-ROOM <USER> <PASSWD>\r\n
//   Answer: OK\n or DENIED\r\n
//
//   Request: SEND-MESSAGE <USER> <PASSWD> <MESSAGE> <ROOM>\n
//   Answer: OK\n or DENIED\n
//
//   Request: GET-MESSAGES <USER> <PASSWD> <LAST-MESSAGE-NUM> <ROOM>\r\n
//   Answer: MSGNUM1 USER1 MESSAGE1\r\n
//           MSGNUM2 USER2 MESSAGE2\r\n
//           MSGNUM3 USER2 MESSAGE2\r\n
//           ...\r\n
//           \r\n
//
//    REQUEST: GET-USERS-IN-ROOM <USER> <PASSWD> <ROOM>\r\n
//    Answer: USER1\r\n
//            USER2\r\n
//            ...
//            \r\n
//

void
IRCServer::processRequest( int fd )
{
// Buffer used to store the comand received from the client
const int MaxCommandLine = 1024;
char commandLine[ MaxCommandLine + 1 ];
int commandLineLength = 0;
int n;

// Currently character read
unsigned char prevChar = 0;
unsigned char newChar = 0;

//
// The client should send COMMAND-LINE\n
// Read the name of the client character by character until a
// \n is found.
//

// Read character by character until a \n is found or the command string is full.
while ( commandLineLength < MaxCommandLine &&
read( fd, &newChar, 1) > 0 ) {

if (newChar == '\n' && prevChar == '\r') {
break;
}

commandLine[ commandLineLength ] = newChar;
commandLineLength++;

prevChar = newChar;
}

// Add null character at the end of the string
// Eliminate last \r
commandLineLength--;
        commandLine[ commandLineLength ] = 0;
printf("\n");
printf("RECEIVED: %s\n", commandLine);

printf("The commandLine has the following format:\n");
printf("COMMAND <user> <password> <arguments>. See below.\n");
printf("You need to separate the commandLine into those components\n");
printf("For now, command, user, and password are hardwired.\n");

/*  command splits here */
char * command = strtok (commandLine, " ");
char * user = strtok (NULL, " ");
char * password = strtok (NULL, " ");
// char * args = strtok (NULL, " ");
char * args = password + strlen(password)+1;

printf("command=%s\n", command);
printf("user=%s\n", user);
printf("password=%s\n", password );
printf("args=%s\n", args);

if (!strcmp(command, "ADD-USER")) {
addUser(fd, user, password, args);
}
else if (!strcmp(command, "CREATE-ROOM")) {
createRoom(fd, user, password, args);
}
else if (!strcmp(command, "ENTER-ROOM")) {
enterRoom(fd, user, password, args);
}
else if (!strcmp(command, "LEAVE-ROOM")) {
leaveRoom(fd, user, password, args);
}
else if (!strcmp(command, "SEND-MESSAGE")) {
sendMessage(fd, user, password, args);
}
else if (!strcmp(command, "GET-MESSAGES")) {
getMessages(fd, user, password, args);
}
else if (!strcmp(command, "GET-USERS-IN-ROOM")) {
getUsersInRoom(fd, user, password, args);
}
else if (!strcmp(command, "GET-ALL-USERS")) {
getAllUsers(fd, user, password, args);
}
else {
const char * msg =  "UNKNOWN COMMAND\r\n";
write(fd, msg, strlen(msg));
}

close(fd);
}

void
IRCServer::initialize()
{


// Open password file
ifstream infile; //input file stream
infile.open( "password.txt");

if(!infile.is_open()) {
cout<< "error while opening file";
}
else {
cout << "file opened successfully" << endl;

// read or write
string line;
while (getline(infile, line)) //this is the command that read line by line
{
cout << "line : " << " " << line << endl;
}

// save user name and password to the map

// username copy/store locally from server

// password copy locally from server


infile.close();
}


}

bool
IRCServer::checkPassword(int fd, const char * user, const char * password) {
// Here check the password
map <string, string>::iterator it = userList.find(user); //finding same user

if (it == userList.end()) {
return false;
}

if ( strcmp(it->second.c_str(), password) == 0)
{
return true; // yes password is the same
}
else {
return false;
}
}

void
IRCServer::addUser(int fd, const char * user, const char * password, const char * args)
{
// Here add a new user. For now always return OK.

FILE *fp = fopen("password.txt", "a"); //open password.txt file, append
//fprintf(fp, "%s:%s\n\n", user, password);


const char * msg =  "OK\r\n";
const char * msg1 = "ERROR: Same user name already existed!!!!!!!!!\r\n";
const char *msg_wrongmatch = "ERROR (Wrong password)\r\n";


/* check redundancy */
//declare iterator 
map <string, string>::iterator it = userList.find(user); //finding same user
if(it != userList.end()) //user exist
{
    write(fd, msg1, strlen(msg1)); //send to the client
}
else {
/* storing user input in the map */
userList.insert(pair<string, string> (user, password));
write(fd, msg, strlen(msg)); //print fd, mssg, length of message
fprintf(fp, "%s:%s\n\n", user, password);

}
fclose(fp); //for password.txt
return;

}
void
IRCServer::createRoom(int fd, const char * user, const char * password, const char * args)
{
// defines message
const char *msg =  "OK\r\n";
const char *msg1 = "DENIED\r\n";
const char *msg2 = "ERROR : same roomname already existed!\r\n";
const char *msg_wrongmatch = "ERROR (Wrong password)\r\n";

//check user, password if wrong -> write DENIED
if (checkPassword(fd, user, password) == false) {
write(fd, msg_wrongmatch, strlen(msg_wrongmatch)); // denied
return;
}

// 2) get all room list
// iterator 
map < string, map < string, vector <string> > >::iterator it;
it = roomList.find(args);
if (it == roomList.end()){ //if room name is not in the roomlist //if( strcmp(args, it->first.c_str() ) != 0){
     //insert roomname in the roomlist

// add roomname and user,message map in the roomlist
map<string, vector<string> > empty;
roomList.insert(pair<string, map<string, vector <string> > > ( args, empty )); 
write(fd, msg, strlen(msg)); // OK
    }

}

void
IRCServer::enterRoom(int fd, const char * user, const char * password, const char * args)
// step 1.1) get all the room list
//  1.2 )check if room existed : yex -> step2 , no -> error message
{

// defines message
const char *msg_ok =  "OK\r\n";
const char *msg_deny = "DENIED\r\n";
const char *msg_noroom = "ERROR (No room)\r\n";
const char *msg_nouser = "no user existed\r\n";
const char *msg_sameuser = "same user name already existed\r\n";
const char *msg_wrongmatch = "ERROR (Wrong password)\r\n";

if (checkPassword(fd, user, password) == false) {
write(fd, msg_wrongmatch, strlen(msg_wrongmatch)); // denied
return;
}

// step1 ) is room exist?
// iterator for find function
map < string, map < string, vector < string > > >::iterator it_room;
it_room = roomList.find(args);
if(it_room == roomList.end()){ //if 'args' is not in the room list
write(fd, msg_noroom, strlen(msg_noroom)); // say "no room existed"
}
else {
// YES : args "r1" is in the roomlist meaing room to enter existed

//check user, password if wrong -> write DENIED
if (checkPassword(fd, user, password) == false) {
write(fd, msg_wrongmatch, strlen(msg_wrongmatch)); // denied
return;
}

// check if user parameter is in the list of users with room parameter (is "u1" in "r1" list? )
// iterator for userlist_roomlist
map <string, vector<string> >::iterator it_user_roomList;
// iterator for userlist
map <string, string>::iterator it_user_total;
// check if user is ever registered in the total userlist
it_user_total = userList.find(user);
// check if user is in the roomlist
it_user_roomList = it_room->second.find(user); 

if (it_user_total == userList.end()){ // if user is not in the userlist
write(fd, msg_nouser, strlen(msg_nouser)); // say no user existed
}
else{ //if user is in the userlist
// is user_total already in user_roomlist? -> if (find(user) == )
// yes -> print "same user already exist", no -> insert into userlist_roomlist
if (it_user_roomList == it_room->second.end()){ //not in the user_roomlist
// insert user in user_total into the userlist_roomlist
vector<string> empty;
it_room->second.insert(pair< string, vector<string> > (user, empty)); // insert user & NULL message in the userlist 
write(fd, msg_ok, strlen(msg_ok)); // ok
}
else{ // yes  parameter "user" is already in the user_roomlist
//print messesage
write(fd, msg_ok, strlen(msg_ok)); // say same user already existed
}
} 
}
}

void
IRCServer::leaveRoom(int fd, const char * user, const char * password, const char * args)
{
// defines message
const char *msg_ok =  "OK\r\n";
const char *msg_deny = "DENIED\r\n";
const char *msg_roomEmpty = "there is no one in the room\r\n";
const char *msg_noroom = "no room existed\r\n";
const char *msg_wrongmatch = "ERROR (Wrong password)\r\n";
const char *msg_nouser = "ERROR (No user in room)\r\n";


///check : is room existed
map < string, map<string, vector<string> > >::iterator it_room;
it_room = roomList.find(args);  // find if "args : parameter roomname" is in the roomlist map
if(it_room == roomList.end()){ // if "argument roomname" is not in the roomlist
write(fd, msg_noroom, strlen(msg_noroom)); // print no room exist
} 
else { // YES, if argument room is in the roomlist

////check user, password if wrong -> write no match in id & pw
if (checkPassword(fd, user, password) == false) {
write(fd, msg_wrongmatch, strlen(msg_wrongmatch)); // no match in uid and pw
return;
}

// check : is no one in the room?
if (roomList.empty() == 1){
write(fd, msg_deny, strlen(msg_deny));
write(fd, msg_roomEmpty, strlen(msg_roomEmpty)); // print msg " no one in the room"
}

//find user
//// iterator for finding userlist_roomlist
map < string, vector<string> >::iterator it_user_roomList;
//find user in userlist in roomlist
it_user_roomList = it_room->second.find(user);
if(it_user_roomList == it_room->second.end()){ // if user is not found in the userlist in roomlist
write(fd, msg_nouser, strlen(msg_nouser)); // print msg nouser message
}
else { // if user is in the list

//remove the arg user if it is already in the roomlist
it_room->second.erase(it_user_roomList);
write(fd, msg_ok, strlen(msg_ok)); // print ok message
}
}
}

void
IRCServer::sendMessage(int fd, const char * user, const char * password, const char * args)
{
// caution
// args splits into "args_roomname" + "args_message"
char * copied_args = strdup(args);
char * args_s_roomname = strtok (copied_args, " ");
char * args_message = strtok(NULL, "\r\n\0"); 

// defines message
const char *msg_ok =  "OK\r\n";
const char *msg_deny = "DENIED\r\n";
const char *msg_roomEmpty = "there is no one in the room\r\n";
const char *msg_noroom = "no room existed\r\n";
const char *msg_wrongmatch = "ERROR (Wrong password)\r\n";
const char *msg_nouser = "ERROR (user not in room)\r\n";

///check : is room existed
map < string, map <string, vector <string> > > ::iterator it_room;

it_room = roomList.find(args_s_roomname); // find the roomname in the list of roomlist
if (it_room == roomList.end()) { // if "argument roomname" is not in the roomlist
write(fd, msg_noroom, strlen(msg_noroom)); // print no room exist
}
else { // YES, if room is existed in the roomlist

//check uesr, password : if wron g-> write no match
if (checkPassword(fd, user, password) == false){
write(fd, msg_wrongmatch, strlen(msg_wrongmatch)); //no match ID & pw
return;
}


//find user
map < string, vector <string> >::iterator it_user_roomList;
//// find user in the userlist in roomlist
it_user_roomList = it_room->second.find(user);
if(it_user_roomList == it_room->second.end()){ //if user is not found in the userlist in roomlist
write(fd, msg_nouser, strlen(msg_nouser));
}
else { // if user is in the list


vector <string> mess = it_user_roomList->second;
string a = to_string(mess.size()) + " " + (string)user + " " + (string)args_message;
mess.push_back(a); // push args to message into the vector<string>
it_user_roomList->second = mess;

write(fd, msg_ok, strlen(msg_ok));//print ok message

it_user_roomList = it_room->second.begin();
for (it_user_roomList = it_room->second.begin(); it_user_roomList != it_room->second.end(); ++it_user_roomList) {
it_user_roomList->second = mess;
}
}
}
}

void
IRCServer::getMessages(int fd, const char * user, const char * password, const char * args)
{

// caution
// args splits into "args_message_start_index" + "args_roomname"
// example : GET-MESSAGES superman clarkkent 2 java-programming
char * copied_args = strdup(args);
char * args_message_start_index = strtok (copied_args, " "); // 2
char * args_g_roomname = strtok (NULL, "\r\n\0"); //java-programming

// defines message
const char *msg_ok =  "OK\r\n";
const char *msg_deny = "DENIED\r\n";
const char *msg_roomEmpty = "there is no one in the room\r\n";
const char *msg_msgEmpty = "there is no message in the message\r\n";
const char *msg_noroom = "no room existed\r\n";
const char *msg_wrongmatch = "ERROR (Wrong password)\r\n";
const char *msg_nouser = "ERROR (User not in room)\r\n";
const char *msg_nonewmessage = "NO-NEW-MESSAGES\r\n";

//check uesr and password and return with wrongmatch message if user or password is wrong
if (checkPassword(fd, user, password) == false){
write(fd, msg_wrongmatch, strlen(msg_wrongmatch)); //no match ID & pw
return;
}

//check whether room is existing or not
map < string, map <string, vector <string> > > ::iterator it_room;

it_room = roomList.find(args_g_roomname); // find if "args : parameter roomname" is in the roomlist map
if (it_room == roomList.end()) { // if argument roomname is not in the roomlist
write(fd, msg_noroom, strlen(msg_noroom)); // print no room exist
}
else { // if argument roomname is in the roomlist

//find user
map < string, vector <string> >::iterator it_user_roomList;
// check user in the userlist
it_user_roomList = it_room->second.find(user);
if(it_user_roomList == it_room->second.end()){ //if user is not found in the userlist in roomlist
write(fd, msg_nouser, strlen(msg_nouser));
}
else { // if user is in the list



//print message stored in the vector <string>

vector <string>::iterator vit_msg; // create iterator for vector
// how to access to vector<string2> in map <string1, vector <string2> > 
// mapname->second 
// GET-MESSAGES mary poppins 0 java-programming
// args splits into "args_message_start_index" + "args_roomname"

// loop : print from begin point +1 til the end
int print_args_message_start_index = (int)(atof(args_message_start_index)); //start from 1 not 0
vector <string> mess = it_user_roomList->second;

int i = print_args_message_start_index + 1;
if (mess.empty() || i >= mess.size()) {
const char * c = "NO-NEW-MESSAGES\r\n";
write(fd, c, strlen(c));
return;
}

for (i = print_args_message_start_index + 1; i < mess.size(); i++) {

string a = mess[i] + "\r\n";
write( fd, a.c_str(), strlen(a.c_str())); //print num

}

const char * c = "\r\n";
write(fd, c, strlen(c));

}
}
}

void
IRCServer::getUsersInRoom(int fd, const char * user, const char * password, const char * args)
//print all the users in the room
{
// defines message
const char *msg_ok =  "OK\r\n";
const char *msg_deny = "DENIED\r\n";
const char *msg_wrongmatch = "ERROR (Wrong password)\r\n";
const char *msg_roomEmpty = "there is no one in the room\r\n";
const char *msg_noroom = "no room existed\r\n";

map < string, map < string, vector < string > > >::iterator it_room;
// roomname	
// user

//does the room exists?
it_room = roomList.find(args);
if(it_room == roomList.end()){ //if 'args' is not in the room list
write(fd, msg_noroom, strlen(msg_noroom)); // say "no room existed"
}
else {

//check password and user and if it is false show up message
if (checkPassword(fd, user, password) == false) {
write(fd, msg_wrongmatch, strlen(msg_wrongmatch)); // denied
return;
}

// check people are in the room
if (roomList.empty() == 1){
write(fd, msg_deny, strlen(msg_deny));
write(fd, msg_roomEmpty, strlen(msg_roomEmpty));
}

// get all users in the room

map < string, vector < string > >::iterator it_room2;

it_room2 = it_room->second.begin(); // iterator at start
for (it_room2 = it_room->second.begin(); it_room2 != it_room->second.end(); ++it_room2){ // loop 'til the end
//iterator 
{
write(fd, it_room2->first.c_str(), strlen(it_room2->first.c_str()) );
write(fd, "\r\n", 2);
}
}

write(fd, "\r\n", 2); //last \r\n
}
}
void
IRCServer::getAllUsers(int fd, const char * user, const char * password, const  char * args)
// print all the users in the list
{

// messages
const char *msg1 = "DENIED\r\n";
const char *msg_wrongmatch = "ERROR (Wrong password)\r\n";

//check password and if it is false show up wrongmatch message
if (checkPassword(fd, user, password) == false) {
write(fd, msg_wrongmatch, strlen(msg_wrongmatch));
return;
}

// get all user
map <string, string>::iterator it;
it = userList.begin();
for (it=userList.begin(); it!=userList.end(); ++it){
write(fd, it->first.c_str(), strlen(it->first.c_str()));
write(fd, "\r\n", 2);
    cout << it->first << " : " << it->second << '\n';
    }
    write(fd, "\r\n", 2);   
}
