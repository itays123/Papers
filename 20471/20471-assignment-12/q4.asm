.data 
	TheCode: .word 0x014b4820, 0x112afffe, 0x8e49ff9c, 0xffffffff
	OpcodeCounter: .space 4 # One byte will be enough since there are maximum 100 instructions
	RegCounter: .space 64 # Two bytes * 32 registers
.text
	.globl main
	
	main:
	li $a0, 0x12345678
	li $a1, 24
	li $a2, 27
	jal takebits
	j end
	
	# Procedure: Take bits
	# Input:
	# 	$a0 - a 32-bit word
	# 	$a1 - the minumum bit, 0-31
	# 	$a2 - the maximum bit, 0-31, $a2 >= $a1
	# Returns $a0[$a2:$a1]
	takebits:
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
		
	
	
	end: