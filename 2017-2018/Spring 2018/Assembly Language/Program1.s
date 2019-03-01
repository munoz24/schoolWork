###########################################################
#		Program Description

###########################################################
#		Register Usage
#	$t0	Contains division number 2
#	$t1	stores number
#	$t2	stores remainder of divided number
#	$t3	stores -1
#	$t4	count for loops
#	$t5	max loop (7)
#	$t6	contains all integers added together
#	$t7	contains calculated average
#	$t8	other loop count
#	$t9
###########################################################
		.data

		intro_p: .asciiz "This Program will display the count of at most 7 (or enter -1 if less then 7) odd non-negative integers, the addition of them and take an average."

		integer_p: .asciiz "Enter odd non-negative integer: "

		error_p: .asciiz "Invalid Number "

		count_p: .asciiz "Count of Valid Numbers: "

		adds_p: .asciiz "Addition of Numbers: "

		dive_p: .asciiz "Average of Numbers: "

nextline_p: .asciiz "\n"    # \n

sum_var_p:      .word 0         # initialize static variable sum_p to zero
avg_var_p:      .word 0         # initialize static variable sum_p to zero
count_var_p:    .word 0         # initialize static variable count_p to zero
###########################################################
		.text
main:
	
intro:
	li $v0, 4
	la $a0, intro_p
	syscall
	
	li $t4, 0		# initializing all registers
	li $t5, 7
	li $t6, 0
	li $t8, 7
	

nums:
	beqz $t8, divs	# if loop count has been reached, end program

	li $t3, -1
	li $t0, 2

	li $v0, 4
	la $a0, integer_p
	syscall

	li $v0, 5
	syscall

	move $t1, $v0		
	beq $t3, $t1, divs	# checks if number is -1
	blez $v0, error		# checks if number is below 0
	
	div $t2, $t1, $t0	
	mfhi $t2		# checks for remainder to see if odd

	beqz $t2, error		# if remainder is 0 then show error
	
	b addition		# if pass all checks goes to add the integer
	
	

error: 
	
	li $v0, 4
	la $a0, error_p
	syscall
	
	b nums

addition:
	addi $t4, $t4, 1	# loop count
	addi $t8, $t8, -1	# other loop count

	add $t6, $t6, $t1	# add integers 

	b nums			# loop back to nums
	
divs:	

	beqz $t4, end		# checks to see if I can divide
	
	div $t7, $t6, $t4	
	
	b end

end:

	la $t9, sum_var_p
	sw $t6, 0($t9)
	

	la $t9, count_var_p
	sw $t4, 0($t9)
	
	la $t9, avg_var_p
	sw $t7, 0($t9)
	
	
	li $v0, 4
	la $a0, count_p
	syscall

	li $v0, 1
	move $a0, $t4
	syscall	

	li $v0, 4
	la $a0, nextline_p
	syscall

	li $v0, 4
	la $a0, adds_p
	syscall

	li $v0, 1
	move $a0, $t6
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

