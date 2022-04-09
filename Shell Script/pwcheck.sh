#!/bin/bash

#DO NOT REMOVE THE FOLLOWING TWO LINES
git add $0 >> .local.git.out
git commit -a -m "Lab 2 commit" >> .local.git.out
git push >> .local.git.out || echo


#Your code here
password=$(cat $1)
length=$(expr length $password)
count=0
if [ $length -lt 6 ]
then
	echo "Error: Password length invalid."
	exit
elif [ $length -gt 32 ]
then 
	echo "Error: Password length invalid."
	exit
else
	#points for each characters in the string
	let count=count+$length
	#if contains special characters
	if [[ $password =~ [#$+%@] ]]; then
		let count=count+5
	fi
	#if contains numbers
	if [[ $password =~ [0-9] ]]; then
		let count=count+5
	fi
	#if contains alphabet characters
	if [[ $password =~ [A-Za-z] ]]; then
		let count=count+5
	fi
	#check repreated alphanumeric characters
	if egrep -q '(.)\1+' $1; then
		let count=count-10
	fi
	#check contains 3 or more consecutive lowercase letter
	if [[ $password =~ [a-z][a-z][a-z]+ ]]; then
		let count=count-3
	fi
	#check contains 3 or more consecutive uppercase letter
	if [[ $password =~ [A-Z][A-Z][A-Z]+ ]]; then
		let count=count-3
	fi
	#check contains 3 or more consecutive numbers
	if [[ $password =~ [0-9][0-9][0-9]+ ]]; then
		let count=count-3
	fi
fi
echo "Password Score: "$count
