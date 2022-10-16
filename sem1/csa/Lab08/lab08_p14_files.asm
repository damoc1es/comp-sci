; Operații cu fișiere text / pb. 14: Se dau un nume de fisier si un text (definite in segmentul de date). Textul contine litere mici, litere mari, cifre si caractere speciale. Sa se transforme toate literele mari din textul dat in litere mici. Sa se creeze un fisier cu numele dat si sa se scrie textul obtinut in fisier.

bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, fprintf ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll     ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fopen msvcrt.dll   ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fclose msvcrt.dll
import fprintf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    nume_fisier DB "lower.out", 0   ; numele fisierului
    text DB "C'eSt lA ViE", 0       ; textul de transformat si pus in fisier
    len EQU $-text-1                ; lungimea textului (-1 deoarece text are un '0' in plus la final)
    
    mod_acces DB "w", 0             ; modul de acces al fisierului, 'w' - scriere
    descriptor_fis DD -1

; our code starts here
segment code use32 class=code
    start:
        ; ...
        
        mov ECX, len        ; ECX = lungimea textului
        mov ESI, 0          ; ESI = 0, indexul de la care incep
        jecxz finalLower                    ; daca ECX = 0, sar peste partea aceasta
        Repeta:                             ; parcurg fiecare litera/caracter
            mov AL, [text+ESI]              ; AL = litera curenta
            mov BL, 'A'                     ; ca sa fie litera mare <=> AL >= 'A' si AL <= 'Z'
            cmp AL, BL                      ; compar AL si BL='A', comparare fara semn deoarece caracterele sunt unsigned
            jb notUpperLetter               ; daca AL below 'A' atunci jump la final
                mov BL, 'Z'                 ; BL = 'Z'
                cmp AL, BL                  ; compar AL si BL='Z'
                ja notUpperLetter           ; daca AL above 'Z' atunci jump la final
                    mov BL, 'a' - 'A'       ; BL = 'a'-'A'
                    add AL, BL              ; in AL este garantat o litera mare deci AL+BL = AL+('a'-'A') transforma AL in litera mica
                    mov [text+ESI], AL      ; suprascriu litera mare cu AL (echivalentul litera mica)
            notUpperLetter:                 ; daca in AL nu a fost litera mare lasam neschimbat octetul
            inc ESI                         ; incrementez ESI
        loop Repeta                         ; repeta de len-1 ori
        finalLower:
        
        
        ; EAX = fopen(nume_fisier, mod_acces)
        push dword mod_acces                ; punem in stiva adresa modului de acces ("w")
        push dword nume_fisier              ; punem in stiva adresa numelui fisierului
        call [fopen]                        ; apelam fopen pentru a crea fisierul
        add ESP, 4*2                        ; eliberam parametrii de pe stiva (2 dword-uri)
        mov [descriptor_fis], EAX           ; salvam valoarea returnata de fopen in variabila descriptor_fis
        
        cmp EAX, 0                          ; verificam daca functia fopen a creat cu succes fisierul (EAX != 0)
        je final                            ; daca a fost o eroare, sare la final
            ; fprintf(descriptor_fis, text)
            push dword text                 ; punem in stiva adresa textul
            push dword [descriptor_fis]     ; punem in stiva descriptorul fisierului
            call [fprintf]                  ; apelam functia de scriere in fisier fprintf
            add ESP, 4*2                    ; eliberam stiva (2 dword-uri)
       
            ; fclose(descriptor_fis)
            push dword [descriptor_fis]     ; punem in stiva descriptorul fisierului
            call [fclose]                   ; apelam functia pentru inchiderea fisierului
            add ESP, 4*1                    ; eliberam stiva (un dword)
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program