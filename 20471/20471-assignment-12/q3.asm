.data 
	buf: .space 21
	buf1: .space 20
	enterString: .asciiz "\nPlease enter a string: \n"

.text
	.globl main
	
	main:
	# Load constants
	li $s0, 20
	
	# Load buffer
	li $v0, 4 # Print arg
	la $a0, enterString
	syscall
	li $v0, 8 # Get input arg
	la $a0, buf
	addi $a1, $s0, 2 # Max capacity of string
	move $t0, $a0
	syscall
	
	# Iterate over chars
	move $t2, $zero
	loop:
		move $t1, $t2 # increase t1 	
		addi $t2, $t1, 1 # increase t2
		lb $t3, buf($t1)
		lb $t4, buf($t2)
		beq $t4, 10, loopEnd # end loop if input ends
		beq $t4, 0, loopEnd 
		# t3=buf[t1], t4=buf[t2]
		beq $t3, $t4, equal
		blt $t3, $t4, smaller
		j bigger
	equal: 
		addi $t5, $zero, 61 #ascii value of =
		j loopContinue
	smaller:
		addi $t5, $zero, 45 #ascii value of -
		j loopContinue
	bigger:
		addi $t5, $zero, 43 #ascii value of +
		j loopContinue
	loopContinue:
		#t5 stores the relevant sign
		sb $t5, buf1($t1)
		j loop
	
	loopEnd:
	# Load buffer
	li $v0, 4 # Print arg
	la $a0, buf1
	syscall
		
		
	