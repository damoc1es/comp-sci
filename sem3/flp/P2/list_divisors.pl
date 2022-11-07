/*
element_divisors(N, K) = | []                            , K >= N
                         | K ⊕ element_divisors(N, K+1) , N%K == 0
                         | element_divisors(N, K+1)      , altfel

element_divisors_W(N) = | element_divisors(-N, 2)   , N < 0
                        | element_divisors(N, 2)    , altfel

my_append(l1 l2 .. ln, r1 r2 .. rm) = | r1 r2 .. rm                         , n = 0
                                      | l1 ⊕ my_append(l2 .. ln, r1 .. rm) , altfel

list_divisors(l1 l2 .. ln) = | []                                                              , n = 0
                             | l1 ⊕ my_append(element_divisors(l1), list_divisors(l2 .. ln))  , altfel

sublist_divisors(l1 l2 .. ln) = | []                                                , n = 0
                                | list_divisors(l1) ⊕ sublist_divisors(l2 .. ln)    , l1 = k1 k2 .. km
                                | l1 ⊕ sublist_divisors(l2 .. ln)                   , altfel
*/

% element_divisors(N: integer, K: integer, R: list)
% model de flux: (i, i, o) sau (i, i, i)
% determinist
% N - numarul al carui divizori ii cautam
% K - numarul natural de la care se incepe cautarea divizorilor (1 < K)
% R - lista divizorilor lui N
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


% element_divisors_W(N: integer, R: list)
% model de flux: (i, o) sau (i, i)
% determinist
% N - numarul al carui divizori ii cautam
% R - lista divizorilor lui N
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


% my_append(L1: list, L2: list, LRez: list)
% model de flux: (i, i, o) sau (i, i, i)
% determinist
% L1 - lista care se adauga in stanga lui L2
% L2 - lista din dreapta lui L1
% LRez - lista rezultanta (LRez = L1 ⊕ L2)
my_append([], T, T).

my_append([H1|T1], T2, [H1|L]) :-
    my_append(T1, T2, L).


% list_divisors(L: list, R: list)
% model de flux: (i, o) sau (i, i)
% determinist
% L - lista care contine elementele dupa care se vor adauga divizorii acestora
% R - lista rezultanta (dupa fiecare element al lui L fiind adaugati divizorii)
list_divisors([], []).

list_divisors([H|T], [H|R]) :-
    element_divisors_W(H, R1),
    list_divisors(T, R2),
    my_append(R1, R2, R).

:- begin_tests(list_divisors).

test("IN: [] OUT: []") :- list_divisors([], []).
test("IN: [6, 2, 1] OUT: [6, 2, 3, 2, 1]") :- list_divisors([6, 2, 1], [6, 2, 3, 2, 1]).
test("IN: [7, 2, 8, 1] OUT: [7, 2, 8, 2, 4, 1]") :- list_divisors([7, 2, 8, 1], [7, 2, 8, 2, 4, 1]).

:- end_tests(list_divisors).


% sublist_divisors(L: list, R: list)
% model de flux: (i, o) sau (i, i)
% determinist
% L - lista care contine subliste la care dupa fiecare element se vor adauga divizorii elementului
% R - lista rezultanta (pt. fiecare lista, dupa fiecare element fiind adaugati divizorii)
sublist_divisors([], []).

sublist_divisors([[H1|T1] | T], [R1|R2]) :-
    list_divisors([H1|T1], R1),
    sublist_divisors(T, R2), !.

sublist_divisors([H|T], [H|R]) :-
    sublist_divisors(T, R).

:- begin_tests(sublist_divisors).

test("IN: [1, [2, 5, 7], 4, 5, [1, 4], 3, 2, [6, 2, 1], 4, [7, 2, 8, 1], 2]
     OUT: [1, [2, 5, 7], 4, 5, [1, 4, 2], 3, 2, [6, 2, 3, 2, 1], 4, [7, 2, 8, 2, 4, 1], 2]") :-
        sublist_divisors([1, [2, 5, 7], 4, 5, [1, 4], 3, 2, [6, 2, 1], 4, [7, 2, 8, 1], 2], [1, [2, 5, 7], 4, 5, [1, 4, 2], 3, 2, [6, 2, 3, 2, 1], 4, [7, 2, 8, 2, 4, 1], 2]).
test("empty list") :- sublist_divisors([], []).
test("empty list and sublist") :- sublist_divisors([[]], [[]]).
test("empty sublist") :- sublist_divisors([4, [], 7, 8], [4, [], 7, 8]).
test("test1") :- sublist_divisors([4, [8], 7, 8], [4, [8, 2, 4], 7, 8]).
test("test2") :- sublist_divisors([4, [8, 6], 7, 8], [4, [8, 2, 4, 6, 2, 3], 7, 8]).
test("test3") :- sublist_divisors([4, [8, 6], 7, [8]], [4, [8, 2, 4, 6, 2, 3], 7, [8, 2, 4]]).
test("only sublists") :- sublist_divisors([[8, 6], [10, 4], [9, 16]], [[8, 2, 4, 6, 2, 3], [10, 2, 5, 4, 2], [9, 3, 16, 2, 4, 8]]).

:- end_tests(sublist_divisors).