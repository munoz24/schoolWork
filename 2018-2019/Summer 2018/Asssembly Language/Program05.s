###########################################################
#		Program Description
#               Program 5
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
average_p: .asciiz "Averege : "
total_p: .asciiz "Total: "
min_p: .asciiz "Minimum value (count * price) is: "
max_p: .asciiz "Maximum value (count * price) is: "

array_pointer: .word 0
array_size: .word 0
###########################################################
		.text
main:

    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, -8

    jal allocate_structure

    lw $t0, 0($sp)
    lw $t1, 4($sp)
    addi $sp, $sp, 8

    lw $ra, 0($sp)
    addi $sp, $sp, 4



    la $t9, array_pointer
    sw $t0, 0($t9)

    la $t9, array_size
    sw $t1, 0($t9)

    la $t9, array_pointer
    lw $t0, 0($t9)

    la $t9, array_size
    lw $t1, 0($t9)



    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, -8
    sw $t0, 0($sp)
    sw $t1, 4($sp)

    jal read_structure

    addi $sp, $sp, 8

    lw $ra, 0($sp)
    addi $sp, $sp, 4



    la $t9, array_pointer
    lw $t0, 0($t9)

    la $t9, array_size
    lw $t1, 0($t9)



    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, -8
    sw $t0, 0($sp)
    sw $t1, 4($sp)

    jal print_structure

    addi $sp, $sp, 8

    lw $ra, 0($sp)
    addi $sp, $sp, 4



    la $t9, array_pointer
    lw $t0, 0($t9)

    la $t9, array_size
    lw $t1, 0($t9)



    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, -24
    sw $t0, 0($sp)
    sw $t1, 4($sp)

    jal sum_average

    l.d $f4, 8($sp)
    l.d $f6, 16($sp)
    addi $sp, $sp, 24

    lw $ra, 0($sp)
    addi $sp, $sp, 4



    li $a0, 10
    li $v0, 11
    syscall

    li $v0, 4
    la $a0, total_p
    syscall

    li $v0, 3
    mov.d $f12, $f4
    syscall

    li $a0, 10
    li $v0, 11
    syscall

    li $v0, 4
    la $a0, average_p
    syscall

    li $v0, 3
    mov.d $f12, $f6
    syscall

    li $a0, 10
    li $v0, 11
    syscall



    la $t9, array_pointer
    lw $t0, 0($t9)

    la $t9, array_size
    lw $t1, 0($t9)



    addi $sp, $sp, -4
    sw $ra, 0($sp)

    addi $sp, $sp, 24
    sw $t0, 0($sp)
    sw $t1, 4($sp)

    jal get_min_max

    l.d $f8, 8($sp)
    l.d $f10, 16($sp)
    addi $sp, $sp, 24

    lw $ra, 0($sp)
    addi $sp, $sp, 4



    li $v0, 4
    la $a0, min_p
    syscall

    li $v0, 3
    mov.d $f12, $f8
    syscall

    li $a0, 10
    li $v0, 11
    syscall

    li $v0, 4
    la $a0, max_p
    syscall

    li $v0, 3
    mov.d $f12, $f10
    syscall


	li $v0, 10		#End Program
	syscall
###########################################################
###########################################################
#		Subprogram Description
#               allocate_structure
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
allocate_p: .asciiz "Enter an array length (data structure size): "
allocate_er_p: .asciiz "Invalid length (data structure size\n"

###########################################################
		.text

allocate_structure:

allocate_loop:

    li $v0, 4
    la $a0, allocate_p
    syscall

    li $v0, 5
    syscall

    bgtz $v0, allocate_valid

    li $v0, 4
    la $a0, allocate_er_p
    syscall

    b allocate_loop

allocate_valid:

    sw $v0, 4($sp)
    move $t1, $v0

    li $t2, 12
    mul $a0, $v0, $t2
    li $v0, 9
    syscall

    sw $v0, 0($sp)
    move $t0, $v0

    li $a0, 10
    li $v0, 11
    syscall


allocate_end:

	jr $ra	#return to calling location
###########################################################
###########################################################
#		Subprogram Description
#               read_structure
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
read_double_p: .asciiz "Enter a double: "
read_int_p: .asciiz "Enter an integer: "

###########################################################
		.text
read_structure:
    lw $t0, 0($sp)
    lw $t1, 4($sp)

read_loop:

    blez $t1, read_end

    li $v0, 4
    la $a0, read_int_p
    syscall

    li $v0, 5
    syscall

    sw $v0, 0($t0)

    li $v0, 4
    la $a0, read_double_p
    syscall

    li $v0, 7
    syscall

    s.d $f0, 4($t0)

    addi $t0, $t0, 12
    addi $t1, $t1, -1

    li $a0, 10
    li $v0, 11
    syscall

    b read_loop


read_end:

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description
#           print_structure
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
print_p: .asciiz "Data table:\n"
###########################################################
		.text

print_structure:

    lw $t0, 0($sp)
    lw $t1, 4($sp)

    li $v0, 4
    la $a0, print_p
    syscall

print_loop:

    blez $t1, print_end

    li $v0, 1
    lw $a0, 0($t0)
    syscall

    li $v0, 11
    li $a0, 9
    syscall

    li $v0, 3
    l.d $f12, 4($t0)
    syscall

    li $a0, 10
    li $v0, 11
    syscall

    addi $t0, $t0, 12
    addi $t1, $t1, -1

    b print_loop


print_end:
	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description
#           sum_average
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
sum_average:

    lw $t0, 0($sp)
    lw $t1, 4($sp)

    li.d $f4, 0.0
    li.d $f6, 0.0

sum_loop:

    blez $t1, sum_end

    lw $t2, 0($t0)
    l.d $f10, 4($t0)

    mtc1 $t2, $f12
    cvt.d.w $f12, $f12

    mul.d $f10, $f10, $f12

    add.d $f4, $f4, $f10
    add.d $f6, $f6, $f12

    addiu $t0, $t0, 12
    addiu $t1, $t1, -1

    b sum_loop

sum_end:
    div.d $f6, $f4, $f6

    s.d $f4, 8($sp)
    s.d $f6, 16($sp)

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description

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

get_min_max:

    lw $t0, 0($sp)
    lw $t1, 4($sp)

    lwc1 $f8, 0($t0)
    cvt.d.w $f8, $f8
    l.d $f4, 4($t0)
    mul.d $f4, $f4, $f8
    mov.d $f6, $f4

get_loop:

    blez $t1, get_end

    lwc1 $f8, 0($t0)
    cvt.d.w $f8, $f8
    l.d $f10, 4($t0)
    mul.d $f10, $f10, $f8

    c.lt.d $f10, $f4

    bc1f get_skip_min

    mov.d $f4, $f10

get_skip_min:

    c.lt.d $f10, $f6
    bc1t get_skip_max

    mov.d $f6, $f10

get_skip_max:

    addiu $t0, $t0, 12
    addiu $t1, $t1, -1

    b get_loop


get_end:

    s.d $f4, 8($sp)
    s.d $f6, 16($sp)

	jr $ra	#return to calling location
###########################################################

