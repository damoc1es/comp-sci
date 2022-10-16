; Programare multi-modul (asm+asm) / pb. 18: Se citeste de la tastatura un sir de numere in baza 10 fara semn. Sa se determine valoarea minima din sir si sa se afiseze in fisierul min.txt (fisierul va fi creat) valoarea minima, in baza 16

; nasm -f obj min_of_string_module.asm
; nasm -f obj lab11_p18.asm
; alink lab11_p18.obj min_of_string_module.obj -oPE -subsys console -entry start

bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf, fopen, fprintf, fclose               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
import scanf msvcrt.dll
import fopen msvcrt.dll
import fprintf msvcrt.dll
import fclose msvcrt.dll

extern min_of_string
; our data is declared here (the variables needed by our program)

segment data use32 class=data
    ; ...
    n dd 0  ; lungimea sirului
    nr dd 0 ; variabila folosita temporar la citirea numerelor
    
    message_input db "Lungimea sirului de numere unsigned (in baza 10): ", 0 ; mesajul de la inceputul programului
    format_input db "%u", 0 ; formatul cu care se citesc numerele (%u - unsigned)
    
    ;err_message db "ERROR: Sirul nu poate avea mai mult de 100 numere!", 0 ; mesaj in caz de n depaseste 100
    format_output db "%x", 0 ; formatul afisarii rezultatului (%x - hex)
    
    
    filename db "min.txt", 0    ; numele fisierului
    mod_acces db "w", 0         ; modul de acces - "w" - write
    descriptor_fisier dd -1     ; descriptorul fisierului
    
    min_val dd 0                ; rezultatul final

    Sir times 101 dd 0          ; sirul de dword
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ; printf(message_input)
        push dword message_input        ; pus pe stiva mesajul pentru a atentiona utilizatorul de limitari
        call [printf]                   ; apel printf(message_input)
        add ESP, 4                      ; eliberez de pe stiva un dword
        
        ; scanf(format_input, n)
        push dword n                    ; pus pe stiva adresa lui n
        push dword format_input         ; pus pe stiva formatul cu care se citeste (%u pt unsigned)
        call [scanf]                    ; apel scanf("%u", &n)
        add ESP, 4*2                    ; eliberez de pe stiva 2 dword-uri
        
        ; cmp dword[n],100                ; compar lungimea sirului cu n
        ; jb continua                     ; daca e mai mica decat 100 atunci pot continua
        
        ; push dword err_message          ; pus pe stiva mesajul de eroare
        ; call [printf]                   ; apel printf(err_message)
        ; add ESP, 4                      ; eliberez de pe stiva un dword
        ; jmp end_program                 ; sar la finalul programului
        
        ; continua:
        mov ECX, [n]                    ; ECX = lungimea sirului
        ;jecxz end_program
        cmp ECX, 0                      ; compar ECX cu 0
        jz end_program                  ; daca ECX=0 jump la finalul programului
        
        cld                             ; directia de la stanga la dreapta
        mov EDI, Sir                    ; EDI = adresa inceputului de sir
        for_read:
            push ECX                    ; salvez valoarea lui ECX
            
            ; scanf(format_input, nr)
            push dword nr               ; pus pe stiva adresa numarului citit
            push dword format_input     ; pus pe stiva formatul citirii
            call [scanf]                ; apel scanf("%u", &nr)
            add ESP, 4*2                ; eliberez de pe stiva 2 dword-uri
            mov EAX, [nr]               ; EAX = numarul
            STOSD                       ; numarul este pus in sir
            
            pop ECX                     ; iau inapoi ECX
        loop for_read                   ; parcurg de ECX ori
        
        ; min_of_string(min_val, Sir, n)
        push dword [n]                  ; pus pe stiva valoarea lungimii sirului
        push dword Sir                  ; pus pe stiva adresa sirului
        push dword min_val              ; pus pe stiva adresa unde se doreste rezultatul
        call min_of_string              ; apel min_of_string(min_val, Sir, n)
        add ESP, 4*3                    ; eliberez de pe stiva 3 dword-uri
        
        ; EAX = fopen(filename, mod_acces)
        push dword mod_acces            ; pus pe stiva modul de acces
        push dword filename             ; pus pe stiva numele fisierului
        call [fopen]                    ; apel EAX = fopen("min.txt", "w")
        add ESP, 4*2                    ; eliberez de pe stiva 2 dword-uri
        
        mov [descriptor_fisier], EAX    ; salvez descriptorul de fisier
        
        cmp EAX, 0                      ; daca EAX = 0 jump la finalul programului
        je end_program                  ; deoarece inseamna ca a fost o eroare
        
        ; fprintf(descriptor_fisier, "%x", min_val)
        push dword [min_val]            ; pus pe stiva valoarea minimului din sir
        push dword format_output        ; pus pe stiva formatul afisarii
        push dword [descriptor_fisier]  ; pus pe stiva descriptorul fisierului
        call [fprintf]                  ; apel fprintf(descriptor_fisier, "%x", min_val) 
        add ESP, 4*3                    ; eliberez de pe stiva 3 dword-uri
        
        ; fclose(descriptor_fisier)
        push dword [descriptor_fisier]  ; pus pe stiva descriptorul fisierului
        call [fclose]                   ; apel fclose(descriptor_fisier), inchidere fisier
        add ESP, 4*1                    ; eliberez de pe stiva un dword
        
        end_program:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
