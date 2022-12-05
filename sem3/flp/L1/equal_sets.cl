; appears(L: list, elem: element)
; model de flux: (i, i) - determinist
; L - lista in care se cauta elementul
; elem - elementul cautat
(defun appears(L elem)
    (cond
        ((null L) 0)
        ((equal (car L) elem) (+ 1 (appears (cdr L) elem)))
        (T (appears (cdr L) elem))
    )
)


; remove_elem(L: list, elem: element)
; model de flux: (i, i) - determinist
; L - lista din care se elimina aparitiile elementului
; elem - elementul eliminat
(defun remove_elem(L elem)
    (cond
        ((null L) nil)
        ((equal (car L) elem) (remove_elem (cdr L) elem))
        (T (cons (car L) (remove_elem (cdr L) elem)))
    )
)


; equal_sets(L: list, R: list, C: list)
; model de flux: (i, i, i) - determinist
; L - prima multime
; R - a doua multime
; C - copie a listei L
(defun equal_sets(L R C)
    (cond
        ((null L)
            (cond 
                ((null R) T)
                ((= (appears C (car R)) (appears R (car R))) (equal_sets L (remove_elem R (car R)) C))
                (T nil)
            )
        )
        ((= (appears R (car L)) (appears L (car L))) (equal_sets (remove_elem L (car L)) R C))
        (T nil)
    )
)


; equal_sets_W(L: list, R: list)
; modele de flux: (i, i) - determinist
; L - prima multime
; R - a doua multime
(defun equal_sets_W(L R)
    (equal_sets L R L))


; (equal_sets_W '() '()) => T
; (equal_sets_W '(1 5 2) '(2 5 1)) => T
; (equal_sets_W '(1 5 2) '(2 5)) => NIL
; (equal_sets_W '(1 1 2) '(1 2)) => NIL
; (equal_sets_W '(1 5) '(1 5 2)) => NIL
