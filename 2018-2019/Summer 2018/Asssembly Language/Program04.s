###########################################################
#		Program Description
#               Program 4
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
original_p:    .asciiz    "\nOriginal Array: "
partition_prompt_p:    .asciiz    "\nEnter a partition value: "
list_less_p:    .asciiz    "All values that less than "
list_greater_p:    .asciiz    "All values that greater than or equal to "
are_p:            .asciiz    " are: "
avg_less_p:        .asciiz    "\nThe average of all elements less than "
avg_greater_p:    .asciiz    "\nThe average of all elements greater than or equal to "
is_p:            .asciiz " is: "

original:    .word    0
original_size:    .word    0
less:        .word    0
less_size:        .word    0
avg_less:        .double    0.0
greater:    .word    0
greater_size:    .word    0
avg_greater:    .double    0.0
###########################################################
		.text
main:

    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, -8

    jal create_array

    lw $t0, 0($sp)
    lw $t1, 4($sp)
    addi $sp, $sp, 8

    lw $ra, 0($sp)
    addi $sp, $sp, 4



    la $t9, original
    sw $t0, 0($t9)

    la $t9, original_size
    sw $t1, 0($t9)

    li $v0, 4
    la $a0, original_p
    syscall



    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, -8
    sw $t0, 0($sp)
    sw $t1, 4($sp)


    addi $sp, $sp, -8
    sw $t0, 0($sp)
    sw $t1, 4($sp)

    jal print_array

    addi $sp, $sp, 8

    lw $t0, 0($sp)
    lw $t1, 4($sp)
    addi $sp, $sp, 8

    lw $ra, 0($sp)
    addi $sp, $sp, 4



    li $v0, 4
    la $a0, partition_prompt_p
    syscall

    li $v0, 7
    syscall

    mov.d $f4, $f0

    li $v0, 11
    la $a0, 10
    syscall



    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, -8
    sw $t0, 0($sp)
    sw $t1, 4($sp)

    addi $sp, $sp, -32
    sw $t0, 0($sp)
    sw $t1, 4($sp)
    s.d $f4, 8($sp)

    jal partition_array

    lw $t2, 16($sp)
    lw $t3, 20($sp)
    lw $t4, 24($sp)
    lw $t5, 28($sp)
    addi $sp, $sp, 32

    lw $t0, 0($sp)
    lw $t1, 4($sp)
    addi $sp, $sp, 8

    lw $ra, 0($sp)
    addi $sp, $sp, 4

    la $t9, less
    sw $t2, 0($t9)

    la $t9, less_size
    sw $t3, 0($t9)

    la $t9, greater
    sw $t4, 0($t9)

    la $t9, greater_size
    sw $t5, 0($t9)

    li $v0, 4
    la $a0, list_less_p
    syscall

    li $v0, 3
    mov.d $f12, $f4
    syscall

    li $v0, 4
    la $a0, are_p
    syscall



    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, -32
    sw $t0, 0($sp)
    sw $t1, 4($sp)
    sw $t2, 8($sp)
    sw $t3, 12($sp)
    sw $t4, 16($sp)
    sw $t5, 20($sp)
    s.d $f4, 24($sp)

    addi $sp, $sp, -8
    sw $t2, 0($sp)
    sw $t3, 4($sp)

    jal print_array

    addi $sp, $sp, 8

    lw $t0, 0($sp)
    lw $t1, 4($sp)
    lw $t2, 8($sp)
    lw $t3, 12($sp)
    lw $t4, 16($sp)
    lw $t5, 20($sp)
    l.d $f4, 24($sp)
    addi $sp, $sp, 32

    lw $ra, 0($sp)
    addi $sp, $sp, 4



    li $v0, 4
    la $a0, list_greater_p
    syscall

    li $v0, 3
    mov.d $f12, $f4
    syscall

    li $v0, 4
    la $a0, are_p
    syscall



    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, -32
    sw $t0, 0($sp)
    sw $t1, 4($sp)
    sw $t2, 8($sp)
    sw $t3, 12($sp)
    sw $t4, 16($sp)
    sw $t5, 20($sp)
    s.d $f4, 24($sp)

    addi $sp, $sp, -8
    sw $t4, 0($sp)
    sw $t5, 4($sp)

    jal print_array

    addi $sp, $sp, 8

    lw $t0, 0($sp)
    lw $t1, 4($sp)
    lw $t2, 8($sp)
    lw $t3, 12($sp)
    lw $t4, 16($sp)
    lw $t5, 20($sp)
    l.d $f4, 24($sp)
    addi $sp, $sp, 32

    lw $ra, 0($sp)
    addi $sp, $sp, 4



    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, -32
    sw $t0, 0($sp)
    sw $t1, 4($sp)
    sw $t2, 8($sp)
    sw $t3, 12($sp)
    sw $t4, 16($sp)
    sw $t5, 20($sp)
    s.d $f4, 24($sp)

    addi $sp, $sp, -16
    sw $t2, 0($sp)
    sw $t3, 4($sp)

    jal get_average

    l.d $f6, 8($sp)
    addi $sp, $sp, 16

    lw $t0, 0($sp)
    lw $t1, 4($sp)
    lw $t2, 8($sp)
    lw $t3, 12($sp)
    lw $t4, 16($sp)
    lw $t5, 20($sp)
    l.d $f4, 24($sp)
    addi $sp, $sp, 32

    lw $ra, 0($sp)
    addi $sp, $sp, 4



    li $v0, 4
    la $a0, avg_less_p
    syscall

    li $v0, 3
    mov.d $f12, $f4
    syscall

    li $v0, 4
    la $a0, is_p
    syscall

    li $v0, 3
    mov.d $f12, $f6
    syscall



    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, -32
    sw $t0, 0($sp)
    sw $t1, 4($sp)
    sw $t2, 8($sp)
    sw $t3, 12($sp)
    sw $t4, 16($sp)
    sw $t5, 20($sp)
    s.d $f4, 24($sp)

    addi $sp, $sp, -16
    sw $t4, 0($sp)
    sw $t5, 4($sp)

    jal get_average

    l.d $f8, 8($sp)
    addi $sp, $sp, 16

    lw $t0, 0($sp)
    lw $t1, 4($sp)
    lw $t2, 8($sp)
    lw $t3, 12($sp)
    lw $t4, 16($sp)
    lw $t5, 20($sp)
    l.d $f4, 24($sp)
    addi $sp, $sp, 32

    lw $ra, 0($sp)
    addi $sp, $sp, 4



    li $v0, 4
    la $a0, avg_greater_p
    syscall

    li $v0, 3
    mov.d $f12, $f4
    syscall

    li $v0, 4
    la $a0, is_p
    syscall

    li $v0, 3
    mov.d $f12, $f8
    syscall



	li $v0, 10		#End Program
	syscall
###########################################################
###########################################################
#		Subprogram Description
#               create_array
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
create_p:    .asciiz    "Enter the array size (n >= 6): "
create_error_p:        .asciiz    "Invalid input!\n"
###########################################################
		.text
create_array:

    li $t9, 6

create_loop:

    li $v0, 4
    la $a0, create_p
    syscall

    li $v0, 5
    syscall

    bge $v0, $t9, create_valid

    li $v0, 4
    la $a0, create_error_p
    syscall

    b create_loop

create_valid:

    move $t1, $v0

    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, -8
    sw $t1, 0($sp)

    jal allocate_array

    lw $t0, 4($sp)
    addi $sp, $sp, 8

    lw $ra, 0($sp)
    addi $sp, $sp, 4



    li $v0, 11
    li $a0, 10
    syscall




    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, -8
    sw $t0, 0($sp)
    sw $t1, 4($sp)

    addi $sp, $sp, -8
    sw $t0, 0($sp)
    sw $t1, 4($sp)

    jal read_array

    addi $sp, $sp, 8

    lw $t0, 0($sp)
    lw $t1, 4($sp)
    addi $sp, $sp, 8

    lw $ra, 0($sp)
    addi $sp, $sp, 4

create_end:

    sw $t0, 0($sp)
    sw $t1, 4($sp)

	jr $ra	#return to calling location
###########################################################
###########################################################
#		Subprogram Description
#               allocate_array
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

    lw $t1, 0($sp)

    li $v0, 9
    sll $a0, $t1, 3
    syscall

    move $t0, $v0

    sw $t0, 4($sp)

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description
#               read_array
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
read_prompt_p:    .asciiz    "Input a double: "
###########################################################
		.text
read_array:

    lw $t0, 0($sp)
    lw $t1, 4($sp)

read_loop:
    blez $t1, read_end

    li $v0, 4
    la $a0, read_prompt_p
    syscall

    li $v0, 7
    syscall

    s.d $f0, 0($t0)

    addi $t0, $t0, 8
    addi $t1, $t1, -1

    b read_loop

read_end:

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description
#           print_array
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

print_array:

    lw $t0, 0($sp)
    lw $t1, 4($sp)

print_loop:

    blez $t1, print_end

    li $v0, 3
    l.d $f12 0($t0)
    syscall

    li $v0, 11
    li $a0, 32
    syscall

    addi $t0, $t0,8
    addi $t1, $t1, -1

    b print_loop

print_end:

    li $v0, 11
    li $a0, 10
    syscall


	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description
#           partition_array
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

partition_array:

    lw $t0, 0($sp)
    lw $t1, 4($sp)
    l.d $f4 8($sp)

    move $t6, $t0
    move $t7, $t1
    li $t3, 0

partition_count:

    blez $t1, partition_create

    l.d $f6, 0($t0)
    c.lt.d $f6, $f4
    bc1f partition_count_update

    addi $t3, $t3, 1

partition_count_update:

    addi $t0, $t0, 8
    addi $t1, $t1, -1

    b partition_count

partition_create:



    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, -8
    sw $t3, 0($sp)

    jal allocate_array

    lw $t2, 4($sp)
    addi $sp, $sp, 8

    lw $ra, 0($sp)
    addi $sp, $sp, 4



    sub $t5, $t7, $t3



    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, -8
    sw $t5, 0($sp)

    jal allocate_array

    lw $t4, 4($sp)
    addi $sp, $sp, 8

    lw $ra, 0($sp)
    addi $sp, $sp, 4



    sw $t2, 16($sp)
    sw $t3, 20($sp)
    sw $t4, 24($sp)
    sw $t5, 28($sp)

partition_fill:

    blez $t7, partition_end

    l.d $f6, 0($t6)
    c.lt.d $f6, $f4
    bc1f partition_greater

    s.d $f6, 0($t2)
    addi $t2, $t2, 8
    b partition_fill_update

partition_greater:
    s.d $f6 0($t4)
    addi $t4, $t4, 8

partition_fill_update:
    addi $t6, $t6, 8
    addi $t7, $t7, -1

    b partition_fill


partition_end:
	jr $ra	#return to calling location
###########################################################

###########################################################
#        Subprogram Description
#               get_average
###########################################################
#        Arguments In and Out of subprogram
#
#    $a0
#    $a1
#    $a2
#    $a3
#    $v0
#    $v1
#    $sp
#    $sp+4
#    $sp+8
#    $sp+12
###########################################################
#        Register Usage
#    $t0
#    $t1
#    $t2
#    $t3
#    $t4
#    $t5
#    $t6
#    $t7
#    $t8
#    $t9
###########################################################
        .data

###########################################################
        .text

get_average:

    lw $t0, 0($sp)
    lw $t1, 4($sp)
    li.d $f8, 0.0

    beqz $t1, get_average_end



    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, -8
    sw $t0, 0($sp)
    sw $t1, 4($sp)

    addi $sp, $sp, -16
    sw $t0, 0($sp)
    sw $t1, 4($sp)

    jal get_sum

    l.d $f4, 8($sp)
    addi $sp, $sp, 16

    lw $t0, 0($sp)
    lw $t1, 4($sp)
    addi $sp, $sp, 8

    lw $ra, 0($sp)
    addi $sp, $sp, 4



    mtc1 $t1, $f6
    cvt.d.w $f6, $f6
    div.d $f8, $f4, $f6

get_average_end:

    s.d $f8, 8($sp)

    jr $ra    #return to calling location
###########################################################

###########################################################
#        Subprogram Description
#           get_sum
###########################################################
#        Arguments In and Out of subprogram
#
#    $a0
#    $a1
#    $a2
#    $a3
#    $v0
#    $v1
#    $sp
#    $sp+4
#    $sp+8
#    $sp+12
###########################################################
#        Register Usage
#    $t0
#    $t1
#    $t2
#    $t3
#    $t4
#    $t5
#    $t6
#    $t7
#    $t8
#    $t9
###########################################################
        .data

###########################################################
        .text

get_sum:

    lw $t0, 0($sp)
    lw $t1, 4($sp)
    li.d $f6, 0.0

get_sum_loop:

    blez $t1, get_sum_end

    l.d $f4, 0($t0)
    add.d $f6, $f6, $f4

    addi $t0, $t0, 8
    addi $t1, $t1, -1

    b get_sum_loop

get_sum_end:

    s.d $f6, 8($sp)

    jr $ra    #return to calling location
###########################################################
