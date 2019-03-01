###########################################################
#		Program Description

###########################################################
#		Register Usage
#	$t0
#	$t1
#	$t2
#	$t3
#	$t4
#	$t5
#	$t6
#	$t7
#	$t8
#	$t9
###########################################################
		.data

create_p:	.asciiz	"Creating an array : \n"
array_base:	.word 	0
array_length:	.word 	0
array_sum:	.word 	0

###########################################################
		.text
main:
	li $v0, 4
	la $a0, create_p
	syscall

	jal allocate_array

	move $t0,$v0
	move $t1,$v1 

	la $t3,array_base
	sw $t0,0($t3) 

	la $t3,array_length
	sw $t1,0($t3)

	move $v0, $t0 
	move $v1, $t1 

	jal read_array

	move $t0,$v0

	la $t3,array_sum
	sw $t0,0($t3)

	la $t3,array_base
	lw $a0,0($t3)

	la $t3,array_length
	lw $a1,0($t3)

	jal print_backwards

	la $t3,array_length
	lw $a1,0($t3)
	la $t3,array_sum
	lw $a0,0($t3)

	jal print_average

	li $v0, 10		#End Program
	syscall
###########################################################
###########################################################
#		Allocate Array

###########################################################
#		Arguments In and Out of subprogram
#
#	$a0
#	$a1
#	$a2
#	$a3
#	$v0
#	$v1
#	$sp
#	$sp+4
#	$sp+8
#	$sp+12
###########################################################
#		Register Usage
#	$t0
#	$t1
#	$t2
#	$t3
#	$t4
#	$t5
#	$t6
#	$t7
#	$t8
#	$t9
###########################################################
		.data
allocate_array_length_p:.asciiz "Enter a array length greater than 0 \n"
allocate_array_error_p:	.asciiz	"Length is less than 0 \n"
###########################################################
		.text

allocate_array:
	
	li $t0, 0 

allocate_loop:

	bgtz $t0, allocate_end 

	li $v0, 4
	la $a0, allocate_array_length_p 
	syscall

	li $v0,5
	syscall 

	move $t0,$v0

	blez $t0, allocate_error 
	b allocate_loop

allocate_error:

	li $v0, 4
	la $a0, allocate_array_error_p  
	syscall

	b allocate_loop

allocate_end:

	li $v0, 9 
	sll $a0,$t0,2
	syscall 

	move $v1, $t0 

	jr $ra	#return to calling location
###########################################################
###########################################################
#		Read Array

###########################################################
#		Arguments In and Out of subprogram
#
#	$a0
#	$a1
#	$a2
#	$a3
#	$v0
#	$v1
#	$sp
#	$sp+4
#	$sp+8
#	$sp+12
###########################################################
#		Register Usage
#	$t0
#	$t1
#	$t2
#	$t3
#	$t4
#	$t5
#	$t6
#	$t7
#	$t8
#	$t9
###########################################################
		.data
read_array_p:	.asciiz "Enter a number into the array that is divisible by 3 and 4: \n"
read_array_error_p : .asciiz "Number does not meet requirements \n"
###########################################################
		.text

read_array:
	li $t2, 0 
	li $t3, 3
	li $t4, 4 
	move $t0, $v0 
	move $t1, $v1

read_array_loop:
	blez $t1, read_array_end
	
	li $v0,4
	la $a0, read_array_p
	syscall

	li $v0,5
	syscall

	bltz $v0, read_array_error 
	rem $t5,$v0,$t3
	bgtz $t5, read_array_error 
	rem $t5,$v0,$t4
	bgtz $t5, read_array_error 

	sw $v0, 0($t0) 
	addi $t0,$t0,4 
	addi $t1,$t1, -1
	add $t2,$t2,$v0

	b read_array_loop

read_array_error:

	li $v0, 4
	la $a0, read_array_error_p #prompt
	syscall

	b read_array_loop

read_array_end:

 	move $v0, $t2


	jr $ra	#return to calling location
###########################################################

###########################################################
#		print Backwards

###########################################################
#		Arguments In and Out of subprogram
#
#	$a0
#	$a1
#	$a2
#	$a3
#	$v0
#	$v1
#	$sp
#	$sp+4
#	$sp+8
#	$sp+12
###########################################################
#		Register Usage
#	$t0
#	$t1
#	$t2
#	$t3
#	$t4
#	$t5
#	$t6
#	$t7
#	$t8
#	$t9
###########################################################
		.data

print_backward_p: .asciiz " The array backwards is : "
print_backward_sp_p: .asciiz " "	
print_backward_nl_p: .asciiz " \n"	

###########################################################
		.text

print_backwards:

	move $t0, $a0 
	move $t1, $a1 
	li $t3, 4
	li $v0,4
	la $a0, print_backward_p
	syscall

	sll $t2, $t1,2
	add $t2,$t2,$t0
	sub $t2,$t2,$t3

print_backwards_loop:
	blt $t2, $t0, print_backwards_end

	li $v0,1
	lw $a0,0($t2)
	syscall

	li $v0,4
	la $a0, print_backward_sp_p
	syscall

	sub $t2,$t2,$t3

	b print_backwards_loop

print_backwards_end:

	li $v0,4
	la $a0, print_backward_nl_p
	syscall

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Print Average

###########################################################
#		Arguments In and Out of subprogram
#
#	$a0
#	$a1
#	$a2
#	$a3
#	$v0
#	$v1
#	$sp
#	$sp+4
#	$sp+8
#	$sp+12
###########################################################
#		Register Usage
#	$t0
#	$t1
#	$t2
#	$t3
#	$t4
#	$t5
#	$t6
#	$t7
#	$t8
#	$t9
###########################################################
		.data
print_average_p: .asciiz "The average of the array is : "
print_average_dot_p: .asciiz "."

###########################################################
		.text

print_average:
 
 	move $t0, $a0
 	move $t1,$a1 

	li $t3, 10 	
	li $t4, 4

 	li $v0,4
	la $a0, print_average_p
	syscall

	div $t0,$t1 
	mflo $t2

	li $v0,1
	move $a0,$t2
	syscall

	li $v0,4
	la $a0, print_average_dot_p
	syscall

	div $t0,$t1
	mflo $t2

print_average_loop:

	blez $t4, print_average_end

	mult $t2, $t3
	mflo $t2
	div $t2, $t1
	mflo $t2

	li $v0,1
	move $a0,$t2
	syscall
	
	add $t4, $t4, -1

	b print_average_loop

print_average_end:

	jr $ra	#return to calling location
###########################################################
