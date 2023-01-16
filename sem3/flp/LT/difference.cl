; inverse_x(l1 l2 .. ln, col) = | col                          , n = 0
;                               | inverse_x(l2 .. ln, l1 U col) , altfel

; inverse(l1 l2 .. ln) = inverse_x(l1 l2 .. ln, [])

; difference(l1 l2 .. ln, r1 r2 .. rm, x) =
;    | []                                                 , n = 0 ∧ m = 0
;    | [10-(l1-x)] U difference(l2 .. ln, [], 1)          , n != 0 ∧ m = 0 ∧ l1-x < 0
;    | [l1-x] U difference(l2 .. ln, [], 0)               , n != 0 ∧ m = 0 ∧ l1-x >= 0
;    | [10-(l1-r1-x)] U difference(l2 .. ln, r2 .. lm, 1) , n != 0 ∧ m != 0 ∧ l1-r1-x < 0
;    | [l1-r1-x] U difference(l2 .. ln, r2 .. lm, 0)      , n != 0 ∧ m != 0 ∧ l1-r1-x >= 0

; main(L, R) = elimina_zero(inverse(difference(inverse(L), inverse(R), 0)))

; elimina_zero(l1 l2 .. ln) = | [0]                    , n = 0
;                             | elimina_zero(l2 .. ln) , l1 = 0
;                             | l1 l2 .. ln            , altfel

; inverse_x(L: list, C: list)
; L - lista care se inverseaza
; C - colectoarea in care este lista inversata
(defun inverse_x (L C)
    (cond
        ((null L) C)
        (t (inverse_x (cdr L) (cons (car L) C)))
    )
)


; inverse(L: list)
; L - lista care trebuie inversata
(defun inverse (L)
    (inverse_x L NIL)
)


; difference(L: list, R: list, x: integer)
; L - prima lista din diferenta
; R - a doua lista din diferenta
; x - depasirea diferentei
(defun difference (L R x)
    (cond
        ((and (null L) (null R)) NIL)
        ((null R) 
            (cond
                ((minusp (- (car L) x)) (cons (+ 10 (- (car L) x)) (difference (cdr L) R 1)))
                (t (cons (- (car L) x) (difference (cdr L) R 0)))
            )
        )
        (t
            (cond
                ((minusp (- (car L) (+ (car R) x))) (cons (+ 10 (- (car L) (+ (car R) x))) (difference (cdr L) (cdr R) 1)))
                (t (cons (- (car L) (+ (car R) x)) (difference (cdr L) (cdr R) 0)))
            )
        )
    )
)


; elimina_zero(L: list)
; L - lista din care se elimina zero-urile de la inceput
(defun elimina_zero (L)
    (cond
        ((null L) (list 0))
        ((= (car L) 0) (elimina_zero (cdr L)))
        (t L)
    )
)


; mainf(L: list, R: list)
; L - prima lista din diferenta
; R - a doua lista din diferenta
(defun mainf (L R)
    (elimina_zero (inverse (difference (inverse L) (inverse R) 0)))
)


; (mainf '(3 3 3) '(1 1 1)) => (2 2 2)
; (mainf '(1 0 0) '(1 0)) => (9 0)
; (mainf '(1 0 0 0) '(1 0)) => (9 9 0)
; (mainf '(1 0 0 0) '(5 0 0)) => (5 0 0)
; (mainf '(6 0) '(8)) => (5 2)
; (mainf '(6 0) '(6 0)) => (0)

; (mainf '(3 3 3) '(1 1 1)) => (2 2 2)
; (mainf '(3 3 3) '(2 2)) => (3 1 1)
; (mainf '(3 3 3) '(2 8)) => (3 0 5)
; (mainf '(1 1 1) '(2 5)) => (8 6)
