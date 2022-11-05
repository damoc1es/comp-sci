element_divisors(N, K, []) :-
    K >= N, !.

element_divisors(N, K, [K|R]) :-
    0 is mod(N, K),
    K1 is K+1,
    element_divisors(N, K1, R), !.

element_divisors(N, K, R) :-
    not(0 is mod(N, K)),
    K1 is K+1,
    element_divisors(N, K1, R).

element_divisors_W(N, R) :-
    N < 0,
    N1 is -N,
    element_divisors(N1, 2, R), !.

element_divisors_W(N, R) :-
    N >= 0,
    element_divisors(N, 2, R).

:- begin_tests(element_divisors).

test("IN: 0 OUT: []") :- element_divisors_W(0, []).
test("IN: 1 OUT: []") :- element_divisors_W(1, []).
test("IN: 2 OUT: []") :- element_divisors_W(2, []).
test("IN: 4 OUT: [2]") :- element_divisors_W(4, [2]).
test("IN: 6 OUT: [2, 3]") :- element_divisors_W(6, [2, 3]).
test("IN: 8 OUT: [2, 4]") :- element_divisors_W(8, [2, 4]).
test("negative numbers == positive counterpart") :- element_divisors_W(-8, [2, 4]).

:- end_tests(element_divisors).

my_append([], T, T).

my_append([H1|T1], T2, [H1|L]) :-
    my_append(T1, T2, L).


list_divisors([], []).

list_divisors([H|T], [H|R]) :-
    element_divisors_W(H, R1),
    list_divisors(T, R2),
    my_append(R1, R2, R).

:- begin_tests(list_divisors).

test("IN: [6, 2, 1] OUT: [6, 2, 3, 2, 1]") :- list_divisors([6, 2, 1], [6, 2, 3, 2, 1]).
test("IN: [7, 2, 8, 1] OUT: [7, 2, 8, 2, 4, 1]") :- list_divisors([7, 2, 8, 1], [7, 2, 8, 2, 4, 1]).

:- end_tests(list_divisors).

sublist_divisors([], []).

sublist_divisors([[H1|T1] | T], [R1|R2]) :-
    list_divisors([H1|T1], R1),
    sublist_divisors(T, R2), !.

sublist_divisors([H|T], [H|R]) :-
    sublist_divisors(T, R).