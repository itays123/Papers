.data 
	buf: .space 21
	buf1: .space 20
	enterString: .asciiz "\nPlease enter a string: \n"
	newLine: .asciiz "\n"
	identicalSeq: .asciiz "\n The number of identical char in row is: "

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
	addi $a1, $s0, 1 # Max capacity of string
	move $t0, $a0
	syscall
	
	# Iterate over chars
	move $t2, $zero
	# Saved temporaries regarding character count
	li $s1, 1 # Max sequence length of same char
	li $s2, 1 # Current .....
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
		# Handle character count
		addi $s2, $s2, 1
		slt $t6, $s1, $s2 # t6=0/1 the number to increase $s1 by
		add $s1, $s1, $t6
		
		j loopContinue
	smaller:
		addi $t5, $zero, 45 #ascii value of -
		li $s2, 1 # Reset counter
		j loopContinue
	bigger:
		addi $t5, $zero, 43 #ascii value of +
		li $s2, 1 # Reset counter
		j loopContinue
	loopContinue:
		#t5 stores the relevant sign
		sb $t5, buf1($t1)
		j loop
	
	loopEnd:
	li $v0, 4 # Print arg
	la $a0, newLine
	syscall
	la $a0, buf1
	syscall
	la $a0, identicalSeq
	syscall
	li $v0, 1 # Print int
	move $a0, $s1
	syscall
		
	
