.text
.global main

main: 	push {fp,lr}
	ldr r0, =ques
	bl printf
	
	ldr r0, =format
	ldr r1, =num
	bl scanf

	ldr r0, =ques2
	bl printf

	ldr r2, =format
	ldr r3, =num2
	bl scanf

	cmp r1,r3
	blt r1_low
	ldr r0, =answer`
	bl printf
	b end
r1_low:
	ldr r0, =answer
	mov r1, r3
	bl printf
	b end
	
.end: pop {fp,pc}

.data
ques: .asciz "Enter an integer value: "
format: .string "%d"
ques2: .asciz "Enter another integer value: "
answer: .asciz "The larger of the two intergers is: %d\n"
num: .word 0
num2: .word 0
