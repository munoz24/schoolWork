###########################################################
#		Program Description
#			Program 3
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
		
array: .word 0
array_size: .word 0
array_reverse: .word 0

original_p: .asciiz "********* THE ONE TRUE ORIGINAL ARRAY ********* \n"		
reverse_p: .asciiz "****** THE FAKE ORIGINAL ARRAY (Reverse) ****** \n"		
sum_p: .asciiz "Sum of odd numbers in array: "
nl_p: .asciiz "\n"
n_p: .asciiz "Enter a stride-N: "
###########################################################
		.text
main:
# jumping to create_array
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	addi $sp, $sp, -12
	
	jal create_array
# getting outputs from create_array 
	lw $t0, 0($sp)
	lw $t1, 4($sp)
	addi $sp, $sp, 12
	
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
# storing variables 
	la $t9, array
	sw $t0, 0($t9)
	
	la $t9, array_size
	sw $t1, 0($t9)
	
# reloading variables
	la $t9, array
	lw $t0, 0($t9)
	
	la $t9, array_size
	lw $t1, 0($t9)
	
# print original_p prompt 
	li $v0, 4
	la $a0, original_p
	syscall

#jumping to print_array
	addi $sp, $sp, -4
	sw $ra, 0($sp)

	addi $sp, $sp, -8
	sw $t0, 0($sp)
	sw $t1, 4($sp)
			
	jal print_array
# reloading stack	
	addi $sp, $sp, 8
	
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
# new line print to console 	
	li $v0, 4
	la $a0, nl_p
	syscall
	
# reloading variables 		
	la $t9, array
	lw $t0, 0($t9)
	
	la $t9, array_size
	lw $t1, 0($t9)
		
# jumping to sum_odd_values
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	addi $sp, $sp, -12
	sw $t0, 0($sp)
	sw $t1, 4($sp)

	jal sum_odd_values
# getting output from stack
	lw $t9, 8($sp)
	addi $sp, $sp, 12				
					
	lw $ra, 0($sp)
	addi $sp, $sp, 4			
	
# printing sum_p prompt
	li $v0, 4
	la $a0, sum_p
	syscall
# printing sum value
	li $v0, 1
	move $a0, $t9
	syscall
# printing new line
	li $v0, 4
	la $a0, nl_p
	syscall
	
#reloading registers
	la $t9, array
	lw $t0, 0($t9)
	
	la $t9, array_size
	lw $t1, 0($t9)
	
# printing reverse_p prompt
	li $v0, 4
	la $a0, reverse_p
	syscall
	
# jumping to reverse_array
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	addi $sp, $sp, -12
	sw $t0, 0($sp)
	sw $t1, 4($sp)
	
	jal reverse_array

#getting output from stack
	lw $t2, 8($sp)
	addi $sp, $sp, 12
	
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
# storing output
	la $t9, array_reverse
	sw $t2, 0($t9)

#loading register
	la $t9, array_size
	lw $t1, 0($t9)
	
#jumping to print_array
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	addi $sp, $sp, -8
	sw $t2, 0($sp)
	sw $t1, 4($sp)
	
	jal print_array

#clearing stack
	addi $sp, $sp, 8
	
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
# printing new line
	li $v0, 4
	la $a0, nl_p
	syscall
	
# printing n_p prompt
	li $v0, 4
	la $a0, n_p
	syscall 

# getting n from user
	li $v0, 5
	syscall

# moving n to $t8
	move $t8, $v0
	
# loading registers
	la $t9, array
	lw $t2, 0($t9)
	
	la $t9, array_size
	lw $t1, 0($t9)
	
# jumping to print_every_nth
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	addi $sp, $sp, -12
	sw $t2, 0($sp)
	sw $t1, 4($sp)
	sw $t8, 8($sp)
	
	jal print_every_nth
	
#clearing stack
	addi $sp, $sp, 12
	
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	
mainEnd:
	li $v0, 10		#End Program
	syscall
###########################################################
###########################################################
#		Subprogram Description
#			create_array
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
create_p: .asciiz "Enter an array size (Larger than Zero): "
create_error_p: .asciiz "Array size is invalid\n"
create_nl_p: .asciiz "\n"
###########################################################
		.text
create_array:
	
	
create_loop:

	li $v0, 4
	la $a0, create_p
	syscall
	
	li $v0, 5
	syscall

	blez $v0, create_error
	
# jumping to allocate_array
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	addi $sp, $sp, -12
	sw $v0, 0($sp)
	
	jal allocate_array
	
# getting outputs from stack
	lw $t0, 4($sp)
	lw $t1, 8($sp)
	addi $sp, $sp, 12
	
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
# jumping to read_array
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	addi $sp, $sp, -8
	sw $t0, 0($sp)
	sw $t1, 4($sp)
	
	addi $sp, $sp, -8
	sw $t0, 0($sp)
	sw $t1, 4($sp)
	
	jal read_array

# clearing stack
	addi $sp, $sp, 8
	
	lw $t0, 0($sp)
	lw $t1, 4($sp)
	addi $sp, $sp, 8
	
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	
	b create_end
	
				
create_error:

	li $v0, 4
	la $a0, create_error_p
	syscall
	
	li $v0, 4
	la $a0, create_nl_p
	syscall
	
	b create_loop
	
create_end:

	li $v0, 4
	la $a0, create_nl_p
	syscall

# storing into stack
	sw $t0, 0($sp)
	sw $t1, 4($sp)						
												
	jr $ra	#return to calling location
###########################################################
###########################################################
#		Subprogram Description
#			allocate_array
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

###########################################################
		.text
allocate_array:

# loading from stack
	lw $t0, 0($sp)

# creating the array
	li $v0, 9 
	sll $a0, $t0, 2
	syscall

# storing into stack
	sw $v0, 4($sp)
	sw $t0, 8($sp)

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description
#			read_array
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
read_p:   .asciiz "Enter an integer(Any value): "
###########################################################
		.text

read_array:

# loading stack
	lw $t0, 0($sp)
	lw $t1, 4($sp)
	
read_loop:

# check to get out of loop
	blez $t1, read_array_end

# prompt for read_p
	li $v0, 4
	la $a0, read_p
	syscall 

# getting user input
	li $v0, 5
	syscall

# storing into input variable
	sw $v0, 0($t0)

# updating counts
	addi $t0, $t0, 4
	addi $t1, $t1, -1
	
	b read_loop

read_array_end:	
		
	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description
#			print_array
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
print_p:   .asciiz     "Array: "
print_space_p:    .asciiz     " " 
print_nl_p:   .asciiz     "\n"
###########################################################
		.text
print_array:

# loading from stack
	lw $t0, 0($sp)
	lw $t1, 4($sp)

# prompt for print_p
	li $v0, 4
	la $a0, print_p
	syscall
	
print_loop:

# initial check
	blez $t1, print_end

# loading from static variable array
	lw $t9, 0($t0)

# printing element
	li $v0, 1
	move $a0, $t9
	syscall

# printing space
	li $v0, 4
	la $a0, print_space_p
	syscall
	
# updating counts and address for next element
	addi $t0, $t0, 4
	addi $t1, $t1, -1
	
	b print_loop
	
print_end:
	
	li $v0, 4
	la $a0, print_nl_p
	syscall
	
	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description
#			print_every_nth
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
print_error_np:		.asciiz	"Invalid value of 'n', elements will not be printed"
print_nl_np:		.asciiz	"\n"
print_space_np:		.asciiz	" "
###########################################################
		.text

print_every_nth: 

# loading from stack
	lw $t0, 0($sp)
	lw $t1, 4($sp)
	lw $t2, 8($sp)

# initial check for error
	blez $t2, print_error

# getting the N-Stride to point to the correct next element
	sll $t3, $t2, 2


print_nloop:

# initial check
	blez $t1, print_nend

# printing the element
	li $v0, 1
	lw $a0, 0($t0)
	syscall
	
	li $v0, 4
	la $a0, print_space_np
	syscall

# updating the counts
	add $t0, $t0, $t3
	sub $t1, $t1, $t2

	b print_nloop

print_error:

	li $v0, 4
	la $a0, print_error_np
	syscall
	
	li $v0, 4
	la $a0, print_nl_np
	syscall
	
	b print_nloop


print_nend:

	li $v0, 4
	la $a0, print_nl_np
	syscall

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description
#		sum_odd_values
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

###########################################################
		.text

sum_odd_values:

# loading from stack
	lw $t0, 0($sp)
	lw $t1, 4($sp)

# initializing variables
	li $t2, 0
	li $t3, 2
	
sum_loop:

# initial check
	blez $t1, sum_end

# loading first element
	lw $t4 0($t0)

# getting remanider
	rem $t5, $t4, $t3

# checking remainder to see if it is even
	bnez $t5, sum_add

# if not even skip the add
	b sum_skip
	
sum_add:

# if even add
	add $t2, $t2, $t4
	
sum_skip:

# updating count
	addi $t0, $t0, 4
	addi $t1, $t1, -1

	b sum_loop
	
sum_end:

# store the sum in stack
	sw $t2, 8($sp)

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description
#			reverse_array
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

###########################################################
		.text

reverse_array:

# loading from stack
	lw $t0, 0($sp)
	lw $t1, 4($sp)
	
	
# jumping to allocate_array
	addi $sp, $sp, -4
	sw $ra, 0($sp)
	
	addi $sp, $sp, -8
	sw $t0, 0($sp)
	sw $t1, 4($sp)
	
	addi $sp, $sp, -12
	sw $t1, 0($sp)

	jal allocate_array

# loading from stack
	lw $t2, 4($sp)
	addi $sp, $sp, 12

# cleaning up stack
	lw $t0, 0($sp)
	lw $t1, 4($sp)
	addi $sp, $sp, 8
	
	lw $ra, 0($sp)
	addi $sp, $sp, 4

# making a copy to later pass in stack
	move $t3, $t2

# updatign counts
	add $t9, $t1, -1
	sll $t9, $t9, 2
	add $t0, $t0, $t9

reverse_loop:

# check to see if last count is done
	blez $t1, reverse_end

# loading array element
	lw $t4, 0($t0)
# storing array element into reverse element
	sw $t4, 0($t3)

# updating counts
	addi $t0, $t0, -4
	addi $t3, $t3, 4
	addi $t1, $t1, -1
	
	b reverse_loop

reverse_end:

# storing address into stack
	sw $t2, 8($sp)

	jr $ra	#return to calling location
###########################################################
