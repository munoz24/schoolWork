###########################################################
#		Program Description

###########################################################
#		Register Usage
#	$t0 input 1
#	$t1 stores 1 (stop anymore inputs)
#	$t2 addition of inputs
#	$t3 Holds division of 2
#	$t4 stores remainder of diveded number
#	$t5
#	$t6 loop count
#	$t7 calculated average
#	$t8
#	$t9 count for total valid inputs
###########################################################
		.data

intro_p:        .asciiz "This Program will display the count of at most 10 (or enter 1 if less then 10) even negative integers, the addition of them and take an average."
integer_p:      .asciiz "Enter even negative integer: "
error_p:        .asciiz "Invalid Number "
count_p:        .asciiz "Count of Valid Numbers: "
adds_p:         .asciiz "Addition of Numbers: "
dive_p:         .asciiz "Average of Numbers: "
nextline_p:     .asciiz "\n"    # \n
sum_var_p:      .word 0         # initialize static variable sum_p to zero
avg_var_p:      .word 0         # initialize static variable sum_p to zero
count_var_p:    .word 0         # initialize static variable count_p to zero
###########################################################
		.text
main:

    li $v0, 4
    la $a0, intro_p #intro string
    syscall

    li $t9, 0   #initializing all regsiters
    li $t2, 0
    li $t6, 10
####################################
count:

    beqz $t6, divs # if loop count has been reached, go to divs

    li $t1, 1
    li $t3, 2

    li $v0, 4
    la $a0, integer_p #prompt user for input
    syscall

#get user's input
    li $v0, 5
    syscall
    move $t0, $v0

#doing checks on input
    beq $t0, $t1, divs
    bgez $t0, error

    div $t4, $t0 ,$t3
    mfhi $t4

    bnez $t4, error

# branch to add input
    b addition

####################################
error:
#print error screen
    li $v0, 4
    la $a0, error_p
    syscall

    b count

addition:
    addi $t9, $t9, 1
    addi $t6, $t6, -1

    add $t2, $t2, $t0    # add integers

# branch back for another loop
    b count

#####################################
divs:
#checking if I can divide (not possible to divide by 0) if not then go to end
    beqz $t2, end

    div $t7, $t2, $t9
#branch to end once divided
    b end

#######################################
end:
#storing all numbers
    la $t5, sum_var_p
    sw $t9, 0($t5)

    la $t5, count_var_p
    sw $t2, 0($t5)

    la $t5, avg_var_p
    sw $t7, 0($t5)
#printing everything

    li $v0, 4
    la $a0, count_p
    syscall

    li $v0, 1
    move $a0, $t9
    syscall

    li $v0, 4
    la $a0, nextline_p
    syscall

    li $v0, 4
    la $a0, adds_p
    syscall

    li $v0, 1
    move $a0, $t2
    syscall

    li $v0, 4
    la $a0, nextline_p
    syscall

    li $v0, 4
    la $a0, dive_p
    syscall

    li $v0, 1
    move $a0, $t7
    syscall

	li $v0, 10		#End Program
	syscall
###########################################################

