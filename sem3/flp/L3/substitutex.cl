; substitutex(X: atom/list, E: element, C: element)
; X - elementul care va fi posibil substituit (daca este lista, se substituie toate nivelurile)
; E - elementul care se substituie
; C - elementul inlocuitor
(defun substitutex (X E C)
    (cond
        ((equal X E) C)
        ((listp X) (mapcar #'(lambda (i) (substitutex i E C)) X))
        (t X)
    )
)


; (substitutex '(A (A B C) (A B C (A B A))) 'A 'Y) => (Y (Y B C) (Y B C (Y B Y)))
; (substitutex '(A B C) 'A '(1 2 3)) => ((1 2 3) B C)
; (substitutex '(A (1 2 3) C) '(1 2 3) 'B) => (A B C)
; (substitutex '(A 2 (1 2 3)) '2 'C) => (A C (1 C 3))
