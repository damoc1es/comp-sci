; pb. 17 / Se dau 2 siruri de octeti S1 si S2 de aceeasi lungime. Sa se construiasca sirul D astfel incat fiecare element din D sa reprezinte maximul dintre elementele de pe pozitiile corespunzatoare din S1 si S2.
; Exemplu:
; S1: 1, 3, 6, 2, 3, 7
; S2: 6, 3, 8, 1, 2, 5
;  D: 6, 3, 8, 2, 3, 7

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
    S1 db 1, 3, 6, 2, 3, 7
    len equ $-S1 ; lungimea sirului
    S2 db 6, 3, 8, 1, 2, 5
    D times len db 0
    ;     6, 3, 8, 2, 3, 7
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ECX, len ; punem lungimea sirului in ecx
        mov ESI, 0 ; indexul de la care incepe fiecare sir
        jecxz Sfarsit ; se ajunge la Sfarsit atunci cand ECX == 0
        Repeta:
            mov AL, [S1+ESI] ; mutam in AL octetul cu indexul ESI din sirul S1 
            mov BL, [S2+ESI] ; mutam in AL octetul cu indexul ESI din sirul S1 
            
            cmp AL,BL ; comparam AL si BL
            jle term ; daca less/equal => DL = BL
                mov DL,AL ; altfel, DL = AL
                jmp dupa ; daca ajunge in else, sare la 'dupa'
            term: ; eticheta term
                mov DL,BL ; daca less/equal compararea anterioara => DL = BL
            dupa: ; maximul va fi in DL
            mov [D+ESI], DL ; punem in DL maximul dintre S1+ESI si S2+ESI
            inc ESI ; incrementam indexul
        loop Repeta ; while
        Sfarsit:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
