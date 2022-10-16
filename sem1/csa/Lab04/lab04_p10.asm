; Lab. 4 / pb. 10 Sa se inlocuiasca bitii 0-3 ai octetului B cu bitii 8-11 ai cuvantului A.

bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

                          
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    a dw 3CBDh ; = 14525 = 0011 *1100* 1011 1101b
    b db 37h ;   =    55 = 0011 *0111*b
; rez ar trebui sa fie     0011  1100b = 3Ch
    rez resb 1

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov AL,[b] ;    AL = b = 37h = 0011 0111b
        and AL, 11110000b ; zerorizez bitii 3-0 a lui b
                          ; AL = 30h = 0011 0000b
                          
        ror word[a],8 ; rotesc la dreapta 8 biti -> bitii 11-8 ajung in 3-0 (a = 003Ch = 0000 0000 0011 1100b)
        mov BL,byte[a] ; BL = primul octet din a, BL = 3Ch = 0011 1100b
        and BL, 00001111b ; zerorizez bitii 7-4 deoarece ma intereseaza doar 3-0, BL = 0Ch = 0000 1100b
        or AL,BL ; cum bitii 3-0 din AL sunt 0 iar bitii 7-4 din BL sunt 0 => AL = 3Ch = 0011 1100b
        mov [rez],AL ; rez = AL = 3Ch = 0011 1100b
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
