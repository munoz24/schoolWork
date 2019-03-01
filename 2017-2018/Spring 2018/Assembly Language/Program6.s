###########################################################
#		Program Description

###########################################################
#		Register Usage
#   $t0  Holds array base address (temporarily)1
#   $t1  Holds array base address (temporarily)1
#   $t2  Holds width height (temporarily)1
#   $t3  Holds array base address (temporarily) 2
#   $t4  Holds array base address (temporarily)2
#   $t5  Holds width height (temporarily)2
#   $t6	 Holds array base address (temporarily) mul
#   $t7  Holds array base address (temporarily) mul
#   $t8  Holds width height (temporarily) mul
###########################################################
		.data
array_pointer1:    .word 0     # holds address of multi-dimensional dynamic array pointer (address)
array_height1:     .word 0     # hold height of multi-dimensional dynamic array (value)
array_width1:      .word 0     # hold width of multi-dimensional dynamic array (value)

array_pointer2:    .word 0     # holds address of multi-dimensional dynamic array pointer (address)
array_height2:     .word 0     # hold height of multi-dimensional dynamic array (value)
array_width2:      .word 0     # hold width of multi-dimensional dynamic array (value)

array_pointer3:    .word 0     # holds address of multi-dimensional dynamic array pointer (address)
array_height3:     .word 0     # hold height of multi-dimensional dynamic array (value)
array_width3:      .word 0     # hold width of multi-dimensional dynamic array (value)

row_p:              .asciiz     "Enter row index (less than array height): "
column_p:           .asciiz     "Enter column index (less than array width): "

###########################################################
		.text
main:
	addi $sp, $sp, -4      
	sw $ra, 0($sp)         

	addi $sp, $sp, -12      

	jal read_col_matrix  

	lw $t0, 0($sp)         
	lw $t1, 4($sp)          
	lw $t2, 8($sp)         
	addi $sp, $sp, 12       

	lw $ra, 0($sp)          
	addi $sp, $sp, 4      


	la $t9, array_pointer1
	sw $t0, 0($t9)          

	la $t9, array_height1
	sw $t1, 0($t9)        

	la $t9, array_width1
	sw $t2, 0($t9)         


	la $t9, array_pointer1
	lw $t0, 0($t9)          

	la $t9, array_height1
	lw $t1, 0($t9)          

	la $t9, array_width1
	lw $t2, 0($t9)          


	addi $sp, $sp, -4       
	sw $ra, 0($sp)          

	addi $sp, $sp, -12     

	sw $t0, 0($sp)        
	sw $t1, 4($sp)         
	sw $t2, 8($sp)          

	jal print_col_matrix    
	addi $sp, $sp, 12       

	lw $ra, 0($sp)          
	addi $sp, $sp, 4

#####################################################


	addi $sp, $sp, -4      
	sw $ra, 0($sp)         

	addi $sp, $sp, -12      

	jal read_col_matrix  

	lw $t0, 0($sp)         
	lw $t1, 4($sp)          
	lw $t2, 8($sp)         
	addi $sp, $sp, 12       

	lw $ra, 0($sp)          
	addi $sp, $sp, 4      


	la $t9, array_pointer2
	sw $t0, 0($t9)          

	la $t9, array_height2
	sw $t1, 0($t9)        

	la $t9, array_width2
	sw $t2, 0($t9)         

	la $t9, array_pointer2
	lw $t0, 0($t9)          

	la $t9, array_height2
	lw $t1, 0($t9)          

	la $t9, array_width2
	lw $t2, 0($t9)          


	addi $sp, $sp, -4       
	sw $ra, 0($sp)          

	addi $sp, $sp, -12     

	sw $t0, 0($sp)        
	sw $t1, 4($sp)         
	sw $t2, 8($sp)          

	jal print_col_matrix    
	addi $sp, $sp, 12       

	lw $ra, 0($sp)          
	addi $sp, $sp, 4    

###################################

	la $t9, array_pointer1
	sw $t0, 0($t9)          

	la $t9, array_height1
	sw $t1, 0($t9)        

	la $t9, array_width1
	sw $t2, 0($t9)         


	la $t9, array_pointer1
	lw $t0, 0($t9)          

	la $t9, array_height1
	lw $t1, 0($t9)          

	la $t9, array_width1
	lw $t2, 0($t9)    



	la $t9, array_pointer2
	sw $t3, 0($t9)          

	la $t9, array_height2
	sw $t4, 0($t9)        

	la $t9, array_width2
	sw $t5, 0($t9)         

	la $t9, array_pointer2
	lw $t3, 0($t9)          

	la $t9, array_height2
	lw $t4, 0($t9)          

	la $t9, array_width2
	lw $t5, 0($t9)        
	

	addi $sp, $sp, -4       
	sw $ra, 0($sp)  


	addi $sp, $sp, -32     

	sw $t0, 0($sp)        
	sw $t1, 4($sp)         
	sw $t2, 8($sp)   

	sw $t3, 12($sp)        
	sw $t4, 16($sp)         
	sw $t5, 20($sp) 


	jal mul_col_matrix

	lw $t6, 24($sp)          # read array base address from stack
	lw $t7, 28($sp)          # read array height from stack
	lw $t8, 32($sp)  
	addi $sp, $sp, 32  

	lw $ra, 0($sp)          
	addi $sp, $sp, 4    


##########################################


	la $t9, array_pointer2
	sw $t0, 0($t9)          

	la $t9, array_height3
	sw $t1, 0($t9)        

	la $t9, array_width3
	sw $t2, 0($t9)         

	la $t9, array_pointer3
	lw $t0, 0($t9)          

	la $t9, array_height3
	lw $t1, 0($t9)          

	la $t9, array_width3
	lw $t2, 0($t9)          


	addi $sp, $sp, -4       
	sw $ra, 0($sp)          

	addi $sp, $sp, -12     

	sw $t6, 0($sp)        
	sw $t7, 4($sp)         
	sw $t8, 8($sp)          

	jal print_col_matrix    
	addi $sp, $sp, 12       

	lw $ra, 0($sp)          
	addi $sp, $sp, 4   




	li $v0, 10		#End Program
	syscall
###########################################################
###########################################################
#		Subprogram Description
#			read_col_matrix
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
read_col_matrix_height_p:     .asciiz "Enter matrix height: "
read_col_matrix_width_p:      .asciiz "Enter matrix width: "
read_col_matrix_invalid_p:    .asciiz "Invalid dimension\n"
###########################################################
		.text

read_col_matrix:

	li $v0, 4               
	la $a0 read_col_matrix_height_p
	syscall

	li $v0, 5               
	syscall

	bgtz $v0, read_col_matrix_height_valid  

	li $v0, 4               
	la $a0, read_col_matrix_invalid_p
	syscall

	b read_col_matrix_height_loop

read_col_matrix_height_valid:
	move $t0, $v0           
	sw $v0, 4($sp)
         
read_col_matrix_width_loop:
	li $v0, 4               
	la $a0, read_col_matrix_width_p
	syscall

	li $v0, 5
	syscall

	bgtz $v0, read_col_matrix_width_valid     

	li $v0, 4              
	la $a0, read_col_matrix_invalid_p
	syscall

	b read_col_matrix_width_loop             

read_col_matrix_width_valid:
	move $t1, $v0           
	sw $v0, 8($sp)  

	li $v0, 9
	mul $a0, $t0, $t1
	sll $a0, $a0, 2
	syscall

	sw $v0, 0($sp)         


	addi $sp, $sp, -4     
	sw $ra, 0($sp)         					

	addiu $sp, $sp, -12     

	sw $v0, 0($sp)         
	sw $t0, 4($sp)          
	sw $t1, 8($sp)        

	jal create_col_matrix     
	addiu $sp, $sp, 12  

	lw $ra, 0($sp)          
	addi $sp, $sp, 4      

read_col_matrix_end:                

	jr $ra	#return to calling location
###########################################################
###########################################################
#		Subprogram Description
#			create_col_matrix
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
create_col_matrix_prompt_p:   .asciiz "Enter an integer: " ###########################################################
		.text
create_col_matrix:

	lw $t0, 0($sp)
	lw $t1, 4($sp)
	lw $t2, 8($sp)


	li $t3, 0

create_col_matrix_loop:
	bge $t3, $t1, create_col_matrix_end
	
	li $t4, 0

create_col_row_matrix_loop:
	bge $t4, $t2, create_col_row_matrix_end
	
	mul $t5, $t4, $t1
	add $t5, $t5, $t3
	sll $t5, $t5, 2
	add $t5, $t5, $t0

	li $v0, 4
	la $a0, create_col_matrix_prompt_p
	syscall
	
	li $v0, 5
	syscall


	sw $v0, 0($t5)
	addi $t4, $t4, 1
	b create_col_row_matrix_loop

	
create_col_row_matrix_end:

	addi $t3, $t3, 1
	b create_col_matrix_loop



create_col_matrix_end:

	jr $ra	#return to calling location
###########################################################

###########################################################
#		Subprogram Description
#			print_col_matrix
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

print_col_matrix:
	lw $t0, 0($sp)
	lw $t1, 4($sp)
	lw $t2, 8($sp)


	move $t9, $t0
	sll $t8, $t1, 2
	li $t3, 0

print_row_matrix_loop:
	bge $t3, $t1, print_col_matrix_end
	
	li $t4, 0
print_col_row_matrix_loop:
	bge $t4, $t2, print_col_row_matrix_end
	
	lw $t5, 0($t0)
	
	li, $v0, 1
	move $a0, $t5
	syscall


	li $v0, 11
	add $t0, $t0, $t8

	addi $t4, $t4, 1
	b print_col_row_matrix_loop

	
print_col_row_matrix_end:

	li $a0, 10
	syscall
	
	addi $t9, $t9, 4
	move $t0, $t9
	addi $t3, $t3, 1
	b print_row_matrix_loop

print_col_matrix_end:
	jr $ra                  # jump back to the main
###########################################################

###########################################################
#		Subprogram Description
#			mul_col_matrix
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

mul_col_matrix:

	lw $t0, 0($sp)
	lw $t1, 4($sp)
	lw $t2, 8($sp)

	lw $t3, 12($sp)
	lw $t4, 16($sp)
	lw $t5, 20($sp)


mul_col_matrix_check:

	bne $t2, $t4, mul_col_matrix_zero
###################################################

	addi $sp, $sp, -4       
	sw $ra, 0($sp)          

	addiu $sp, $sp, -12     

	sw $v0, 0($sp)         
	sw $t4, 4($sp)          
	sw $t2, 8($sp)         

	jal create_col_matrix    
	addi $sp, $sp, 12     

	lw $ra, 0($sp)          
	addi $sp, $sp, 4 

##################################################


mul_col_matrix_create:


	lw $v0, 0($sp)
	lw $t4, 4($sp)
	lw $t2, 8($sp)


	li $t6, 0

mul_col_matrix_loop:
	bge $t3, $t1, mul_col_matrix_end
	
	li $t7, 0

mul_col_row_matrix_loop:

	bge $t7, $t2, mul_col_row_matrix_pre_end
	
	mul $t8, $t6, $t4
	add $t8, $t8, $t6
	sll $t8, $t8, 2
	add $t8, $t8, $v0

	


	addi $sp, $sp, -4       
	sw $ra, 0($sp)          

	addi $sp, $sp, -24      				

	sw $t0, 0($sp)         
	sw $t1, 4($sp)         
	sw $t2, 8($sp)       
	sw $t3, 12($sp)         
	sw $t4, 16($sp)         
	sw $t5, 20($sp)   
	
	jal multiply

	lw $t9, 24($sp)        
	addi $sp, $sp, 24       
					
	lw $ra, 0($sp)          
	addi $sp, $sp, 4        


	sw $t9, 0($t8)
	addi $t7, $t7, 1
	b mul_col_row_matrix_loop

	
mul_col_row_matrix_end:

	addi $t6, $t6, 1
	b read_col_matrix_loop


mul_col_row_matrix_pre_end:
	
	sw $v0, 24($sp) 
	sw $t4, 28($sp) 
	sw $t2, 32($sp)
	b mul_col_matrix_end


mul_col_matrix_zero:

	li $t6, 0
	sw $t6, 24($sp) 
	sw $t6, 28($sp) 
	sw $t6, 32($sp)



mul_col_matrix_end:

	jr $ra	#return to calling location
###########################################################
###########################################################
#		Subprogram Description
#			multiply
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
multiply:
	lw $t0, 0($sp)
	lw $t1, 4($sp)
	lw $t2, 8($sp)

	lw $t3, 12($sp)
	lw $t4, 16($sp)
	lw $t5, 20($sp)



multiply_loop:


########## Stuck here. Not sure how to do the multiplication of matrix with very little registers 
li $t9, 9   
sw $t9, 24($sp)    #just to see if my allocation of size works

	jr $ra	#return to calling location
###########################################################