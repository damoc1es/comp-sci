; appears(L: list, elem: element)
; L - lista in care se cauta elementul
; elem - elementul cautat
(defun appears(L elem)
    (cond
        ((null L) NIL)
        ((equal (car L) elem) T)
        (T (appears (cdr L) elem))
    )
)


; equal_sets(L: list, R: list, C: list)
; L - prima multime
; R - a doua multime
; C - copie a listei L
(defun equal_sets(L R C)
    (cond
        ((null L)
            (cond 
                ((null R) T)
                ((appears C (car R)) (equal_sets L (cdr R) C))
                ; (T nil)
            )
        )
        ((appears R (car L)) (equal_sets (cdr L) R C))
        (T NIL)
    )
)


; equal_sets_W(L: list, R: list)
; L - prima multime
; R - a doua multime
(defun equal_sets_W(L R)
    (equal_sets L R L))


; (equal_sets_W '() '()) => T
; (equal_sets_W '(1 5 2) '(2 5 1)) => T
; (equal_sets_W '(1 5 2) '(2 5)) => NIL
; (equal_sets_W '(1 2) '(1 2)) => T
; (equal_sets_W '(1 5) '(1 5 2)) => NIL
