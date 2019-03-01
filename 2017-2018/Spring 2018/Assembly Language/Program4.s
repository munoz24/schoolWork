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
source_adress : .word 0
source_length : .word 0

partition_number : .double 0.0
partition_p : .asciiz "Enter a number to partition the array : " 

less_adress : .word 0
less_length : .word 0

greater_adress : .word 0
greater_length : .word 0

source_prompt : .asciiz "Original array: "

less_prompt : .asciiz "Less Partition array: "
greater_prompt : .asciiz "Greater Partition array: "


less_ave_p : .asciiz "Less Partition average is: "
greater_ave_p: .asciiz "Greater Partition average is: "

###########################################################
		.text
main:

addi $sp,$sp,-4
sw $ra,0($sp) #store Ra

addi $sp,$sp,-8 #for two arguments out

jal create_array

lw $t0, 0($sp)
lw $t1, 4($sp)
addi $sp,$sp,8

lw $ra,0($sp)
addi $sp,$sp,4

la $t9, source_adress
sw $t0,0($t9)

la $t9,source_length
sw $t1, 0($t9)

li $v0,4
la $a0, partition_p 
syscall

li $v0, 7
syscall

la $t9, partition_number
s.d $f0,0($t9)

addi $sp,$sp,-4
sw $ra,0($sp)

addi $sp,$sp,-8
sw $t0,0($sp)
sw $t1,4($sp)

addi $sp,$sp, -28 
sw $t0,0($sp)
sw $t1,4($sp) 
s.d $f0, 8($sp) 

jal partition_array

lw $t2,12($sp) 
lw $t3,16($sp) 
lw $t4,20($sp) 
lw $t5,24($sp) 
addi $sp,$sp, 28 

lw $t0,0($sp)
lw $t1,4($sp)
addi $sp,$sp,8 

lw $ra,0($sp)
addi $sp,$sp,4 

la $t9,greater_adress
sw $t2,0($t9)

la $t9,greater_length 
sw $t3,0($t9)

la $t9,less_adress 
sw $t4,0($t9)

la $t9,less_length 
sw $t5,0($t9)

li $v0,4
la $a0, source_prompt
syscall

addi $sp,$sp,-4
sw $ra,0($sp)

addi $sp,$sp,-24 
sw $t0,0($sp)
sw $t1,4($sp)
sw $t2,8($sp)
sw $t3,12($sp)
sw $t4,16($sp)
sw $t5,20($sp)

addi $sp,$sp,-8 
sw $t0,0($sp)
sw $t1,4($sp)

jal print_array

addi $sp,$sp,8

lw $t0,0($sp)
lw $t1,4($sp)
lw $t2,8($sp)
lw $t3,12($sp)
lw $t4,16($sp)
lw $t5,20($sp)
addi $sp,$sp,24 

lw $ra,0($sp)
addi $sp,$sp,4 



li $v0,4
la $a0, greater_prompt
syscall

addi $sp,$sp,-4
sw $ra,0($sp)

addi $sp,$sp,-24 
sw $t0,0($sp)
sw $t1,4($sp)
sw $t2,8($sp)
sw $t3,12($sp)
sw $t4,16($sp)
sw $t5,20($sp)
			
addi $sp,$sp,-8 
sw $t2,0($sp)
sw $t3,4($sp)

jal print_array

addi $sp,$sp,8

lw $t0,0($sp)
lw $t1,4($sp)
lw $t2,8($sp)
lw $t3,12($sp)
lw $t4,16($sp)
lw $t5,20($sp)
addi $sp,$sp,24 

lw $ra,0($sp)
addi $sp,$sp,4 


li $v0,4
la $a0, less_prompt
syscall

addi $sp,$sp,-4
sw $ra,0($sp)

addi $sp,$sp,-24 
sw $t0,0($sp)
sw $t1,4($sp)
sw $t2,8($sp)
sw $t3,12($sp)
sw $t4,16($sp)
sw $t5,20($sp)
			
addi $sp,$sp,-8 
sw $t4,0($sp)
sw $t5,4($sp)

jal print_array

addi $sp,$sp,8

lw $t0,0($sp)
lw $t1,4($sp)
lw $t2,8($sp)
lw $t3,12($sp)
lw $t4,16($sp)
lw $t5,20($sp)
addi $sp,$sp,24 

lw $ra,0($sp)
addi $sp,$sp,4 





addi $sp,$sp,-4 
sw $ra,0($sp)

addi $sp,$sp,-24
sw $t0,0($sp)
sw $t1,4($sp)
sw $t2,8($sp)
sw $t3,12($sp)
sw $t4,16($sp)
sw $t5,20($sp)

addi $sp,$sp,-12 
sw $t4,0($sp)
sw $t5,4($sp)

jal get_ave

l.d $f4,8($sp)
addi $sp,$sp,12

lw $t0,0($sp)
lw $t1,4($sp)
lw $t2,8($sp)
lw $t3,12($sp)
lw $t4,16($sp)
lw $t5,20($sp)
addi $sp,$sp,24 

lw $ra,0($sp)
addi $sp,$sp,4 




la $v0,4
la $a0,less_ave_p
syscall

la $v0,3
mov.d $f0,$f4
syscall

addi $sp,$sp,-4 
sw $ra,0($sp)

addi $sp,$sp,-24 
sw $t0,0($sp)
sw $t1,4($sp)
sw $t2,8($sp)
sw $t3,12($sp)
sw $t4,16($sp)
sw $t5,20($sp)

addi $sp,$sp,-12 
sw $t2,0($sp)
sw $t3,4($sp)

jal get_ave

l.d $f6,8($sp)
addi $sp,$sp,12

lw $t0,0($sp)
lw $t1,4($sp)
lw $t2,8($sp)
lw $t3,12($sp)
lw $t4,16($sp)
lw $t5,20($sp)
addi $sp,$sp,24 

lw $ra,0($sp)
addi $sp,$sp,4 

la $v0,4
la $a0,greater_ave_p
syscall

la $v0,3
mov.d $f0,$f6
syscall

	li $v0, 10		#End Program
	syscall
###########################################################
###########################################################
#		Subprogram Description

#		create_array
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

create_array:

li $t0,0

addi $sp,$sp,-4
sw $ra,0($sp)

addi $sp,$sp,-12 
sw $t0,0($sp)

jal allocate_array	

lw $t0,0($sp)
lw $t1,4($sp)
addi $sp,$sp, 12 

lw $ra,0($sp)
addi $sp,$sp, 4


addi $sp,$sp, -4 
sw $ra,0($sp)

addi $sp,$sp, -8 
sw $t0,0($sp)
sw $t1,4($sp)

addi $sp,$sp, -8 
sw $t0,0($sp)
sw $t1,4($sp)

jal read_array

addi $sp,$sp,8

lw $t0,0($sp)
lw $t1,4($sp)
addi $sp,$sp,8

lw $ra,0($sp)
addi $sp,$sp,4

create_array_end:
sw $t0,0($sp)
sw $t1,4($sp)

	jr $ra	#return to calling location
###########################################################
###########################################################
#		Subprogram Description

#		allocate_array
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
allocate_array_p : .asciiz "Enter a positive number greater than 6: "
allocate_array_error_p : .asciiz "Number is not greater than 6 "

###########################################################
		.text

allocate_array:

lw $t0,0($sp)
li $t9,6 

allocate_array_loop:

li $v0,4
la  $a0, allocate_array_p 
syscall

li $v0, 5
syscall

blt $v0,$t9, allocate_array_error

move $t1,$v0

li $v0,9
sll $a0,$t1, 3
syscall 

move $t0,$v0

b create_array_end


allocate_array_error:

li $v0,4 
la  $a0, allocate_array_error_p
syscall

li $v0,11 
li  $a0, 35
syscall

b allocate_array_loop



allocate_array_end:

sw $t0,0($sp)
sw $t1,4($sp)

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description

#		read_array
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
read_array_p : .asciiz "Enter a number(double-precision): "

###########################################################
		.text

read_array:
lw $t0, 0($sp)
lw $t1, 4($sp)  

read_array_loop:

blez $t1, read_array_end

li $v0,4
la $a0, read_array_p
syscall

li $v0,7
syscall

s.d $f0,0($t0)

addi $t0,$t0,8
addi $t1,$t1, -1

b read_array_loop

read_array_end:

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description

#		partition_array
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

lw $t0,0($sp)
lw $t1,4($sp) 
li $t2,0 
li $t4,0 
l.d $f4,8($sp)

partition_loop_size:

blez $t1, partition_create_arrays

l.d $f6,0($t0)

c.lt.d $f4,$f6
bc1f partition_greater

addi $t0,$t0,8
addi $t1,$t1,-1
addi $t2,$t2,1

b partition_loop_size 



partition_greater:

addi $t0,$t0,8
addi $t1,$t1,-1
addi $t4,$t4,1
b partition_loop_size



partition_create_arrays:

sw $t2,16($sp)
sw $t4,24($sp)

li $v0,9 
sll $a0,$t2,3
syscall

move $t3,$v0
sw $t3,12($sp) 

li $v0,9 
sll $a0,$t4,3
syscall

move $t5,$v0 
sw $t5,20($sp)

lw $t0,0($sp) 
lw $t1,4($sp) 



partition_array_loop:

blez $t1,partition_array_end

l.d $f6,0($t0)

c.lt.d $f4,$f6
bc1f partition_array_greater

s.d $f6,0($t3)
addi $t0,$t0,8
addi $t1,$t1,-1

b partition_array_loop



partition_array_greater:
s.d $f6,0($t5)
addi $t0,$t0,8
addi $t1,$t1,-1

b partition_array_loop



partition_array_end:

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description

#		print_array
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
lw $t0,0($sp)
lw $t1,4($sp)

print_array_loop:
blez $t1, print_array_end
    
li $v0,3
l.d $f12,0($t0)
syscall

li $v0,11 # print space
li $a0,32
syscall

addi $t0, $t0,8            
addi $t1, $t1, -1           
b print_array_loop
         
print_array_end:

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description

#		get_sum
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

get_sum:

lw $t0,0($sp)
lw $t1,4($sp)
li.d $f4,0.00

get_sum_loop:

blez $t1,get_sum_end

l.d $f8 0($t0)
add.d $f4,$f4,$f8
addi $t0,$t0,8
addi $t1,$t1,-1

b get_sum_loop


get_sum_end:

s.d $f4, 8($sp)

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description

#		get_ave

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

get_ave:

lw $t0,0($sp)
lw $t1,4($sp)
li.d $f6,0.00
li.d $f8,0.0 


addi $sp,$sp,-4 
sw $ra,0($sp)

addi $sp,$sp,-12 
sw $t0,0($sp)
sw $t1,4($sp)
s.d $f6,8($sp)

addi $sp,$sp,-12
sw $t0,0($sp)
sw $t1,4($sp)

jal get_sum

l.d $f4,8($sp)
addi $sp,$sp,12 

lw $t0,0($sp)
lw $t1,4($sp)
l.d $f4,8($sp)
addi $sp,$sp,12 

lw $ra,0($sp)
addi $sp,$sp,4

c.le.d $f8,$f4
bc1t get_ave_end

mtc1 $t1,$f10
cvt.d.w $f10,$f10
div.d $f6,$f4,$f10

get_ave_end:

s.d $f6,12($sp)


	jr $ra	#return to calling location
###########################################################

