.data 
	TheCode: .word 0x014b4820, 0x112afffe, 0x8e49ff9c, 0xffffffff
	OpcodeCounter: .space 16 # 4 Counters for 4 kinds of insturctions
	RegCounter: .space 128 # 4 bytes * 32 registers
	
	InvalidOpcodeErr: .asciiz "\n Note: invalid opcode in word\n"
	DestIsZero: .asciiz "\n Note: encountered $rd=0 in R-type instruction \n"
	BranchEnsured: .asciiz "\n Note: encountered $rs=$rt case in beq instruction \n"
	TargetIsZero: .asciiz "\n Note: encountered $rt=0 in lw instruction \n"
	
	TableHeader: .asciiz "\nInst code/ Reg \t appearances\n"
	RType: .asciiz "R-Type"
	Beq: .asciiz "beq"
	LW: .asciiz "lw"
	SW: .asciiz "sw"
.text
	# Saved temps
	# $s0 - current offset from address of word
	# $s1 - current word
	# $s2 - current opcode
	# $s3 - current rs value
	# $s4 - current rt value
	# $s5 - current rd value, if opcode=0
	# $s6 - terminator 0xffffffff
	# $s7 - "index" in OpcodeCounter to increase
	
	.globl main
	
	main:
	la $s0, TheCode
	addi $s0, $s0, -4
	li $s6, 0xffffffff
	loop:
		addi $s0, $s0, 4
		lw $s1, 0($s0)
		beq $s1, $s6, end
		# Process word
		move $a0, $s1
		jal takeOpcode
		move $s2, $v0
		jal takeRegisters
		move $s3, $v0
		move $s4, $v1
		# Increase register counters
		la $a0, RegCounter
		move $a1, $s3
		jal increase
		move $a1, $s4
		jal increase
		# branch according to opcode
		beq $s2, 0, handleRType
		beq $s2, 0x4, handleBeq
		beq $s2, 0x23, handleLW
		beq $s2, 0x2b, handleSW
		# Invalid opcode: Print error (TODO)
		li $v0, 4
		la $a0, InvalidOpcodeErr
		syscall
		j loop
	handleRType:
		li $s7, 0 # Index to increase later
		# Continue processing - take rd
		move $a0, $s1
		jal takeDst
		move $s5, $v0
		# increase register counter for rd
		la $a0, RegCounter
		move $a1, $s5
		jal increase
		# Special case - rd=0 (TODO)
		bne $s5, 0, loopContinue
		li $v0, 4
		la $a0, DestIsZero
		syscall
		j loopContinue
	handleBeq:
		li $s7, 1 # Index to increase later
		# Special case - rt=rs (TODO)
		bne $s3, $s4, loopContinue
		li $v0, 4
		la $a0, BranchEnsured
		syscall
		j loopContinue
	handleLW:
		li $s7, 2 # Index to increase later
		# Special case - rt=0 (TODO)
		bne $s4, $zero, loopContinue
		li $v0, 4
		la $a0, TargetIsZero
		syscall
		j loopContinue
	handleSW:
		li $s7, 3 # Index to increase later
	loopContinue:
		# Increase matching instruction counter
		la $a0, OpcodeCounter
		move $a1, $s7
		jal increase
		j loop
	
	# Procedure: Take bits
	# Input:
	# 	$a0 - a 32-bit word
	# 	$a1 - the minumum bit, 0-31
	# 	$a2 - the maximum bit, 0-31, $a2 >= $a1
	# Returns $a0[$a2:$a1]
	takeBits:
		li $t0, 31 # The maximum number of bits
		# Create mask
		sub $t1, $a2, $a1 # t1 - (the number of bits -1)
		sub $t2, $t0, $a2 # t2 - the number of bits to shift right
		li $t3, 0x80000000 # 100000.....0000
		srav $t3, $t3, $t1
		srlv $t3, $t3, $t2
		# Mask input
		and $t4, $a0, $t3
		srlv $v0, $t4, $a1
		jr $ra
		
	# Procedure: Take opcode
	# Input:
	# 	$a0 - a 32-bit word
	# Returns $a0[31:26]
	takeOpcode:
		# Take arguments
		li $a1, 26
		li $a2, 31
		# Save $ra
		addi $sp, $sp, -4
		sw $ra, 0($sp)
		jal takeBits
		lw $ra, 0($sp)
		addi $sp, $sp, 4
		jr $ra
	
	# Procedure: Take registers rs, rt
	# Procedure: Take opcode
	# Input:
	# 	$a0 - a 32-bit word
	# Returns $v0=$a0[25:21], $v1=$a0[20:16]
	takeRegisters:
		# Take arguments
		li $a1, 16
		li $a2, 20
		# Save $ra
		addi $sp, $sp, -4
		sw $ra, 0($sp)
		jal takeBits
		add $v1, $zero, $v0 # Move result
		li $a1, 21
		li $a2, 25
		jal takeBits
		# Recover stack
		lw $ra, 0($sp)
		addi $sp, $sp, 4
		jr $ra
	
	# Procedure: Take destination register for R-type instructions
	# Input:
	# 	$a0 - a 32-bit word
	# Returns $a0[15:11]
	takeDst:
		# Take arguments
		li $a1, 11
		li $a2, 15
		# Save $ra
		addi $sp, $sp, -4
		sw $ra, 0($sp)
		jal takeBits
		lw $ra, 0($sp)
		addi $sp, $sp, 4
		jr $ra
	
	# Procedure: Add 1 to counter
	# Input:
	#	$a0 - a base address of word array
	# 	$a1 - "index" to increase, starting from 0, in words
	increase:
		# Load current value
		sll $t0, $a1, 2 # t0 is now the offset in bytes
		add $t1, $a0, $t0 # t1 is the address of word to increase
		lw $t2, 0($t1)
		addi $t2, $t2, 1
		sw $t2, 0($t1)
		jr $ra
	
	end:
	# Print results
	li $v0, 4
	la $a0, TableHeader
	syscall
	# Print instructions
	la $s0, OpcodeCounter
	li $a1, 4
	
	la $a0, RType
	lw $a2, 0($s0)
	jal PrintRow
	
	la $a0, Beq
	lw $a2, 4($s0)
	jal PrintRow
	
	la $a0, LW
	lw $a2, 8($s0)
	jal PrintRow
	
	la $a0, SW
	lw $a2, 12($s0)
	jal PrintRow
	
	# Print registers
	li $a1, 1
	la $s0, RegCounter
	li $s1, -1 # Register name
	printLoop:
		addi $s1, $s1, 1
		beq $s1, 32, terminate
		sll $t0, $s1, 2 # t0 is the offset
		add $t1, $s0, $t0
		lw $a2, 0($t1)
		beq $a2, $zero, printLoop # do not print row if appearences is 0
		move $a0, $s1
		jal PrintRow
		j printLoop
	
	
	# Procedure: Print table row
	# Input
	# 	$a0 - row title (int/string)
	#	$a1 - row title type (int=1, string=4 just like syscalls)
	# 	$a2 - row result
	# Results: v0=11
	PrintRow:
		move $t0, $a0
		li $v0, 11
		li $a0, '\n'
		syscall
		move $v0, $a1
		move $a0, $t0
		syscall
		li $v0, 11
		li $a0, '\t'
		syscall
		li $v0, 1
		move $a0, $a2
		syscall
		jr $ra
	
	terminate:
