###########################################################
#		Program Description

###########################################################
#		Register Usage
#	$t0 Jump Register
#	$t1 Jump Register
#	$t2
#	$t3 Temp Register
#	$t4
#	$t5
#	$t6
#	$t7
#	$t8
#	$t9
###########################################################
		.data

create_p:    .asciiz    "Let's create an array! \n"
count_p:    .asciiz "\nCount: "
sum_p:  .asciiz "Sum: "

array_base: .word 0
array_length:   .word 0
array_sum:  .word 0

###########################################################
		.text
main:
#Prompt
    li $v0, 4
    la $a0, create_p
    syscall
#jumping to subprogram
    jal allocate_array
#getting info from subprogram
    move $t0, $v0
    move $t1, $v1
#stroring info
    la $t3, array_base
    sw $t0, 0($t3)

    la $t3, array_length
    sw $t1, 0($t3)
#preparing to jump
    move $v0, $t0
    move $v1, $t1
#jumping
    jal read_array
#coming back
    move $t0, $v0
#storing
    la $t3, array_sum
    sw $t0, 0($t3)
#printing sum
    li $v0, 4
    la $a0, sum_p
    syscall

    li $v0, 1
    lw $a0, 0($t3)
    syscall
#printing count
    li $v0, 4
    la $a0, count_p
    syscall

    li $v0, 1
    la $t3, array_length
    lw $a0, 0($t3)
    syscall
#getting ready to jump
    la $t3, array_base
    lw $t0, 0($t3)

    la $t3, array_length
    lw $t1, 0($t3)

    move $v0, $t0
    move $v1, $t1
#jumping
    jal print_backwards
#getting address and length
    la $t3, array_sum
    lw $t0, 0($t3)

    la $t3, array_length
    lw $t1, 0($t3)

    move $v0, $t0
    move $v1, $t1
#jumping
    jal print_average

exit:

	li $v0, 10		#End Program
	syscall
###########################################################
###########################################################
#		Subprogram Description
#           Allocate Array
###########################################################
#		Arguments In and Out of subprogram
#
#	$a0
#	$a1
#	$a2
#	$a3
#	$v0 Length
#	$v1 Base
#	$sp
#	$sp+4
#	$sp+8
#	$sp+12
###########################################################
#		Register Usage
#	$t0 Holds Length
#	$t1 Holds Base
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
allocate_array_length_p: .asciiz "Enter an array length greater than 0: "
allocate_array_error_p:  .asciiz "Invalid length: less or equal than 0 \n"
###########################################################
		.text

allocate_array:


allocate_loop:

    li $v0, 4
    la $a0, allocate_array_length_p
    syscall

    li $v0, 5
    syscall

    move $t0, $v0

    blez $t0, allocate_error
    b allocate_end

allocate_error:

    li $v0, 4
    la $a0, allocate_array_error_p
    syscall

    b allocate_loop

allocate_end:

    li $v0, 9
    sll $a0, $t0, 2
    syscall

    move $v1, $t0

	jr $ra	#return to calling location
###########################################################
###########################################################
#		Subprogram Description
#               Read Values
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
#	$t0 Base
#	$t1 Length
#	$t2 Sum of valid inputs read
#	$t3 Holds 2
#	$t4 Holds 3
#	$t5 Holds remainder of divison of 2 or 3
#	$t6
#	$t7
#	$t8
#	$t9 Holds input
###########################################################
		.data
read_array_p:   .asciiz "\nEnter a number into the array that is divisible by 2 and 3: "
read_array_error_p: .asciiz "Number does not meet requirements \n"
###########################################################
		.text

read_array:
#getting base and length
    move $t0, $v0
    move $t1, $v1
#loading registers
    li $t2, 0
    li $t3, 2
    li $t4, 3

read_array_loop:
#check
    blez $t1, read_array_end
#prompt
    li $v0, 4
    la $a0, read_array_p
    syscall
#getting input
    li $v0, 5
    syscall

    move $t9, $v0
#checking to see if input is valid
    bltz $t9, read_array_error
    rem $t5, $t9, $t3
    bgtz $t5, read_array_error
    rem $t5, $t9, $t4
    bgtz $t5, read_array_error
#storing input into array
    sw $t9, 0($t0)
#next index
    addi $t0, $t0, 4
#updating count
    addi $t1, $t1, -1
#
    add $t2, $t2, $t9
#branch back for more inputs
    b read_array_loop

read_array_error:

    li $v0, 4
    la $a0, read_array_error_p
    syscall

    b read_array_loop

read_array_end:
#returning valid inputs read
    move $v0, $t2

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description
#           Print Backwards
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
#	$t0 Base
#	$t1 Length
#	$t2 Holds array index
#	$t3 Holds 4
#	$t4
#	$t5
#	$t6
#	$t7
#	$t8
#	$t9
###########################################################
		.data
print_backward_p:   .asciiz "\nThe array backwards is : "
print_backward_sp_p:    .asciiz " "
print_backward_nl_p:    .asciiz "\n"
###########################################################
		.text

print_backwards:
#storing base and length
    move $t0, $v0
    move $t1, $v1
#setting registers
    li $t3, 4
#prompt
    li $v0, 4
    la $a0, print_backward_p
    syscall
#getting to the back of the array
    sll $t2, $t1, 2
    add $t2, $t2, $t0
#decreasing to next index
    sub $t2, $t2, $t3

print_backwards_loop:
#check
    blt $t2, $t0, print_backwards_end
#getting element
    li $v0, 1
    lw $a0, 0($t2)
    syscall
#printing
    li $v0, 4
    la $a0, print_backward_sp_p
    syscall
#updating index
    sub $t2, $t2, $t3

    b print_backwards_loop

print_backwards_end:

    li $v0 4
    la $a0, print_backward_nl_p
    syscall

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description
#               Print Average
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
#	$t0 Base
#	$t1 Length
#	$t2 Holds Quotient of Average
#	$t3
#	$t4
#	$t5
#	$t6
#	$t7
#	$t8
#	$t9
###########################################################
		.data
print_average_p:    .asciiz "\nThe average of the array is: "
print_average_dot_p: .asciiz "."
###########################################################
		.text

print_average:
#getting base and length
    move $t0, $v0
    move $t1, $v1

    li $t3, 10
    li $t4, 4
#prompt
    li $v0, 4
    la $a0, print_average_p
    syscall
#dividing total by count
    div $t0, $t1
    mflo $t2
#printing quotient and dot
    li $v0, 1
    move $a0, $t2
    syscall

    li $v0, 4
    la $a0, print_average_dot_p
    syscall

    div $t0,$t1
    mfhi $t2

print_average_loop:
#getting
    blez $t4, print_average_end

    mult $t2, $t3
    mflo $t2
    div $t2, $t3
    mfhi $t2

    li $v0, 1
    move $a0, $t2
    syscall

    add $t4, $t4, -1

    b print_average_loop

print_average_end:

	jr $ra	#return to calling location
###########################################################

