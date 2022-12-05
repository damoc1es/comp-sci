; intercal(L: list, elem: element, N: integer)
; modele de flux: (i, i, i) - determinist
; L - lista in care se va adauga elementul
; elem - elementul adaugat
; N - pozitia pe care se adauga
(defun intercal(L elem N)
    (cond
        ((= N 1)  (cons elem L))
        ((null L) NIL)
        ((> N 1)  (cons (car L) (intercal (cdr L) elem (- N 1))))
        (T        L)
    )
)


; (intercal '(1 2 3) 4 1) => (4 1 2 3)
; (intercal '(1 2 3) 4 3) => (1 2 4 3)
; (intercal '(1 2 3) 4 6) => (1 2 3)
