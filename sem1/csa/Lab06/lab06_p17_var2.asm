; pb. 17 / var2: parcurgere in sens invers; Se da un sir de dublucuvinte. Sa se ordoneze descrescator sirul cuvintelor inferioare ale acestor dublucuvinte. Cuvintele superioare raman neschimbate.
; Exemplu:
; sir DD 12345678h, 1256ABCDh, 12AB4344h
; rez:   1234ABCDh, 12565678h, 12AB4344h

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
    ;          ____       ____       ____
    sir dd 12345678h, 1256ABCDh, 12AB4344h ; sirul initial
    ; in memorie 78 56 34 12 | CD AB 56 12 | 44 43 AB 12
    lend equ ($-sir)/4 ; lungimea dublucuvintelor
    lenw equ lend*2 ; lungimea cuvintelor
    lenb equ lend*4
    
    rez times lend dw 0
        
        ; La final
        ; 1234ABCDh, 12565678h, 12AB4344h
        ; in memorie: CD AB 34 12 | 78 56 56 12 | 44 43 AB 12
        
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ESI, sir+lenb-4 ; mutam indexul la sirul initial
        mov EDI, rez+lenw-2 ; rez va fi sirul in care facem sortarea
        
        std ; DF = 1, mergem de la dreapta la stanga
        mov ECX, lend
        repeta:
            movsw ; mut fiecare cuvant inferior al dublucuvintelor in rez
            dec ESI
            dec ESI ; decrementam de 2 ori deoarece movsw decrementeaza doar de 2 ori si lucrez cu dublucuvinte in ESI
        loop repeta
        
        mov EAX, 1 ; presupunem sirul nu este sortat (EAX = ok)
        sort: ; sort pe rez (cuvinte)
            std
            mov ESI, rez+lend-1 ; aducem ESI la final
            cmp EAX, 0 ; comparam EAX cu 0
            je final ; daca EAX e 0 atunci tot sirul este ordonat corespunzator
            mov EAX, 0 ; EAX = 0
            mov ECX, lend-1 ; comparam elementele
            pentru:
                mov DX, [ESI] ; DX = primul element
                cmp DX, [ESI+2] ; compar cu urmatorul cuvant
                jae continua ; DX <= [ESI] executa
                    mov BX, [ESI+2] ; interschimbam cuvintele
                    mov [ESI+2], DX
                    mov [ESI], BX
                    mov EAX, 1 ; marcam faptul ca s-a produs o schimbare => nu e sortat complet
                continua:
                dec ESI ; decrementam de doua ori deoarece lucram cu cuvinte
                dec ESI ; si noi avem stocate dublucuvinte, sarim peste un cuvant
            loop pentru 
        jmp sort
            
        final: ; rez e sortat descrescator
        
        mov ECX, lend ; ECX = lungimea sirului
        mov ESI, rez+lenw-2 ; ESI - unde incepe rez
        mov EDI, sir+lenb-4 ; EDI - unde incepe sir
        
        std ; DF = 1, mergem de la dreapta la stanga
        
        repeta2:
            movsw ; inlocuiesc cuvintele inferioare ale lui "sir" cu cuvintele din "rez"
            dec EDI
            dec EDI
        loop repeta2
            
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
