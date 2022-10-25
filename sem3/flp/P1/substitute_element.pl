/*
Sa se scrie un predicat care substituie intr-o lista un element printr-o alta lista.

substitute_elem(l1 l2 .. ln, K, r1 r2 .. rm) = | []                                                                , n = 0
                                               | my_append(r1 r2 .. rm, substitute_elem(l2 .. ln, K, r1 r2 .. rm)) , l1 = K
                                               | l1 ⊕ substitute_elem(l2 .. ln, K, r1 r2 .. rm)                   , altfel

my_append(l1 l2 .. ln, r1 r2 .. rm) = | r1 r2 .. rm                         , n = 0
                                      | l1 ⊕ my_append(l2 .. ln, r1 .. rm) , altfel
my_append(L, R) = L ⊕ R
*/

% my_append(L1: list, L2: list, LRez: list)
%          (i, i, o) (i, i, i) determinist
% L1 : lista care se adauga in stanga lui L2
% L2 : lista din dreapta

% daca L1 este vid, am terminat concatenarea
my_append([], T, T).

% se tot adauga primul element al primei liste la a doua lista
my_append([H1|T1], T2, [H1|L]) :-
    my_append(T1, T2, L).


:- begin_tests(my_append).

test("1") :- my_append([], [1, 2], [1, 2]).
test("2") :- my_append([1, 2], [], [1, 2]).
test("3") :- my_append([], [], []).
test("4") :- my_append([1, 2], [3, 4], [1, 2, 3, 4]).

:- end_tests(my_append).


% substitute_element(L1: list, K: element, L2: list, LRez)
%                   (i, i, i, o) (i, i, i, i) determinist
% L1 : lista din care se substituie
% K : elementul care este inlocuit
% L2 : lista care substituie elementul K
% LRez : lista rezultanta

% daca lista este goala, returnez lista
substitute_element([], _, _, []).

% daca primul element din lista este K, concatenez la continuarea rezolvarii
substitute_element([H|T], K, L, R2) :-
    H = K,
    substitute_element(T, K, L, R),
    my_append(L, R, R2), !.

% daca primul element != K, iau primul element si il adaug la rezolvare
substitute_element([H|T], K, L, [H|R]) :-
    H \= K,
    substitute_element(T, K, L, R).


% run_tests.
:- begin_tests(substitute_element).

test("1") :- substitute_element([], 8, [1, 2], []).
test("2") :- substitute_element([7, 8, 9], 8, [1, 2], [7, 1, 2, 9]).
test("3") :- substitute_element([7, 8, 9], 8, [1], [7, 1, 9]).
test("4") :- substitute_element([7, 8, 9], 8, [], [7, 9]).
test("5") :- substitute_element([7, 8, 9], 7, [1, 2], [1, 2, 8, 9]).
test("6") :- substitute_element([7, 8, 9], 9, [1, 2], [7, 8, 1, 2]).
test("7") :- substitute_element([7, 8, 9], 6, [1, 2], [7, 8, 9]).
test("8") :- substitute_element([1, 1, 2, 3, 2, 5], 2, [7, 8], [1, 1, 7, 8, 3, 7, 8, 5]).

:- end_tests(substitute_element).