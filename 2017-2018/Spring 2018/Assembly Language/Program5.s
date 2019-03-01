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

structure_b : .word 0
structure_s : .word 0
sum_num : .double 0.0
average_num : .double 0.0
min_num : .double 0.0
max_num : .double 0.0

###########################################################
		.text
main:

addi $sp,$sp,-4
sw $ra,0($sp)

addi $sp,$sp,-8

jal allocate_structure

lw $t0,0($sp) 
lw $t1,4($sp)
addi $sp,$sp,8

lw $ra,0($sp)
addi $sp,$sp,4

la $t9,structure_b
sw $t0,0($t9)

la $t9,structure_s
sw $t1,0($t9)



addi $sp,$sp,-4
sw $ra,0($sp)

addi $sp,$sp,-8
sw $t0,0($sp) 
sw $t1,4($sp) 

addi $sp,$sp,-8  
sw $t0,0($sp) 
sw $t1,4($sp) 

jal read_structure

addi $sp,$sp,8 

lw $t0,0($sp) 
lw $t1,4($sp)
addi $sp,$sp,8

lw $ra,0($sp)
addi $sp,$sp,4




addi $sp,$sp,-4 
sw $ra,0($sp)

addi $sp,$sp,-8 
sw $t0,0($sp) 
sw $t1,4($sp) 

addi $sp,$sp,-8  
sw $t0,0($sp) 
sw $t1,4($sp) 

jal print_structure

addi $sp,$sp,8 

lw $t0,0($sp) 
lw $t1,4($sp)
addi $sp,$sp,8 

lw $ra,0($sp)
addi $sp,$sp,4 



#addi $sp,$sp,-4 
#sw $ra,0($sp)

#addi $sp,$sp,-8 
#sw $t0,0($sp) 
#sw $t1,4($sp) 

#addi $sp,$sp,-20
#sw $t0,0($sp) 
#sw $t1,4($sp) 

#jal sum_average

#l.d $f4,8($sp)
#l.d $f6,16($sp)
#addi $sp,$sp,20 

#lw $t0,0($sp) 
#lw $t1,4($sp) 
#addi $sp,$sp,8 

#lw $ra,0($sp)
#addi $sp,$sp,4

#la $t9,sum_num
#s.d $f4,0($t9)

#la $t9,average_num 
#s.d $f6,0($t9)




addi $sp,$sp,-4
sw $ra,0($sp)

addi $sp,$sp,-20 
sw $t0,0($sp) 
sw $t1,4($sp) 
s.d $f4,8($sp)
s.d $f6,16($sp)

addi $sp,$sp,-20 
sw $t0,0($sp) 
sw $t1,4($sp) 

jal get_min_max

l.d $f8,8($sp) 
l.d $f10,16($sp) 
addi $sp,$sp,20

lw $t0,0($sp) 
lw $t1,4($sp) 
l.d $f4,8($sp)
l.d $f6,16($sp)
addi $sp,$sp,20

lw $ra,0($sp)
addi,$sp,$sp,4

la $t9,min_num
s.d $f8,0($t9)

la $t9,max_num
s.d $f10,0($t9)

	li $v0, 10		#End Program
	syscall
###########################################################
###########################################################
#		Subprogram Description
#			allocate_structure
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

allocate_structure_p : .asciiz "Enter a size greater than 0: "
allocate_structure_error_p : .asciiz "Size is not greater than zero."
###########################################################
		.text
allocate_structure:

li $t2,12

li $v0,4
la $a0,allocate_structure_p 
syscall

li $v0,5 
syscall

blez $v0, allocate_structure_error

move $t1,$v0 

li $v0,9 
mul $a0,$t1,$t2
syscall

move $t0,$v0 

b allocate_structure_end


allocate_structure_error:

li $v0,4
la $a0,allocate_structure_error_p
syscall
b allocate_structure


allocate_structure_end:

sw $t0,0($sp)
sw $t1,4($sp) 



	jr $ra	#return to calling location
###########################################################
###########################################################
#		Subprogram Description
#			read_structure
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
read_integer_p:  .asciiz "Enter an integer value: "
read_double_p:   .asciiz "Enter a double value: "

###########################################################
		.text
read_structure:

lw $t0,0($sp)
lw $t1,4($sp) 

read_structure_loop:

blez $t1,read_structure_end 

li $v0,4
la $a0,read_double_p 
syscall

li $v0,7 
syscall
 
s.d $f0,0($t0)

li $v0,4
la $a0,read_integer_p 
syscall

li $v0,5 
syscall

sw $v0,8($t0) 

addi $t0,$t0,12
addi $t1,$t1,-1 

b read_structure_loop


read_structure_end:
	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description
#			print_structure
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
print_structure_p:  .asciiz "Input Structure:\n"
print_l : .asciiz "\n"
###########################################################
		.text
print_structure:

lw $t0,0($sp)  
lw $t1,4($sp)  

li $v0,4
la $a0,print_structure_p
syscall

print_structure_loop:
blez $t1,print_structure_end 

li $v0,3 
l.d $f12,0($t0)
syscall

li $v0,11
li $a0,9
syscall

li $v0,1
lw $a0,8($t0)
syscall

li $v0,4 
la $a0,print_l 
syscall

addi $t0,$t0,12 
addi $t1,$t1,-1 

b print_structure_loop

print_structure_end:

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description
#			sum_average
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

lw $t0,0($sp) 
lw $t1,4($sp) 
li.d $f4,0.0 
li.d $f6,0.0 

beqz $t1,sum_average_end

sum_average_loop: 
blez $t1,sum_average_m

l.d $f8,0($t0)             #this is where I get an error in Single Step (Bad Address) 

lw $t9,8($t0)		   #I tried using program 4 by combining both get sum and get average

mtc1 $t9,$f10 

cvt.d.w $f10,$f10

mul.d $f12,$f8,$f10 
add.d $f4,$f4,$f12 

addi $t0,$t0,12             #Would this be the issue? Could both not use $t0 or is +12 wrong?
addi $t1,$t1,1
b sum_average_loop

sum_average_m: 

mtc1 $t1,$f10 

cvt.d.w $f10,$f10 
div.d $f6,$f4,$f10


sum_average_end:
s.d $f4,8($sp)
s.d $f6,16($sp)

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description
#			get_min_max
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

lw $t0,0($sp)  
lw $t1,4($sp)  
li.d $f4,0.0 
li.d $f6,0.0 

l.d $f8,0($t0)
lwc1 $f10,8($t0) 
cvt.d.w $f10,$f10 
mul.d $f4,$f8,$f10 
mov.d $f6,$f4 

addi $t1,$t1,-1 
addi $t0,$t0,12

get_min_max_loop:
beqz $t1,get_min_max_end


l.d $f8,0($t0) 
lw $t9,8($t0)		  

mtc1 $t9,$f10 
cvt.d.w $f10,$f10 
mul.d $f8,$f8,$f10

c.lt.d $f4,$f8
bc1t get_min_update

c.lt.d $f6,$f8
bc1t get_max_update

addi $t1,$t1,-1
addi $t0,$t0,12
b get_min_max_loop

get_min_update: 

mov.d $f4,$f8
addi $t1,$t1,-1
addi $t0,$t0,12
b get_min_max_loop

get_max_update:

mov.d $f6,$f8
addi $t1,$t1,-1
addi $t0,$t0,12
b get_min_max_loop

get_min_max_end:
s.d $f4,8($sp)
s.d $f6,16($sp)

	jr $ra	#return to calling location
###########################################################

