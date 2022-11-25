.data
	TheCode: .word 0x014b4820, 0x112afffe, 0x8e49ff9c, 0xffffffff
	OpcodeCounter: .space 4 # One byte will be enough since there are maximum 100 instructions
	RegCounter: .space 64 # Two bytes * 32 registers
.text
	# REGISTER PURPOSE
	# $t1 - index in the code. initialize at 0
	# $t2 - current word in the code
	# $t3 - mask
	# $t4 - masked value (opcode, register) to be shifted and compared
	# $t5 - index in Opcode Counter (0=R-type, 1=beq, 2=lw, 3=sw)
	# $t6 - index in Reg Counter
	# $s1 - current opcode
	# $s2 - current rs
	# $s3 - current rt
	# $s4 - current rd, if existing
	
	.globl main
	
	main:
	addi $t1, $zero, -4
	mainLoop:
		addi $t1, $t1, 4
		lw $t2, TheCode($t1)
		beq $t2, 0xffffffff, mainLoopEnd # stop condition
		# Find opcode
		li $t3, 0xfc000000 # Bits 26-31
		and $t4, $t2, $t3
		srl $s1, $t4, 26
		# Find rs
		li $t3, 0x03e00000 # Bits 21-25
		and $t4, $t2, $t3
		srl $s2, $t4, 21
		# Find rt
		li $t3, 0x001f0000 # Bits 16-20
		and $t4, $t2, $t3
		srl $s2, $t4, 16
		# Load $t5 with matching index. and send to subsequent jobs
		beq $s1, 0, handleRType
		beq $s1, 0x4, handleBeq
		beq $s1, 0x23, handleLw
		beq $s1, 0x2b, handleSw
		j handleInvalidOpcode
	handleRType:
		# TODO: handle rd=0
		li $t5, 0
		j mainLoopContinue
	handleBeq:
		li $t5, 1
		# TODO: handle rs=rt
		j mainLoopContinue
	handleLw:
		li $t5, 2
		# TODO: Handle rt=0
		j mainLoopContinue
	handleSw:
		li $t5, 3
		j mainLoopContinue
	handleInvalidOpcode:
		# TODO: Print error
		j mainLoop # jump to next word
	mainLoopContinue:
		# Increase matching counter
		j mainLoop
	
	mainLoopEnd:
	# Print results
	