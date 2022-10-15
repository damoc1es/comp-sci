% Sa se elimine elementul de pe pozitia a n-a a unei liste liniare.
%
% remove_nth(l1 l2 .. ln, N) =  |  []                            , n = 0
%                               |  [l2 .. ln]                    , N = 1
%                               |  l1 ⊕ remove_nth(l2 .. ln, N-1), altfel

remove_nth([], _, []).

remove_nth([_|T], N, T) :-
    1 is N.

remove_nth([H|T], N, [H|R]) :-
    not(1 is N),
    N1 is N-1,
    remove_nth(T, N1, R).

% TESTS:
%
% remove_nth([7,8,9], 1, [8,9]).
% remove_nth([7,8,9], 2, [7,9]).
% remove_nth([7,8,9], 3, [7,8]).
% remove_nth([7,8,9], 4, [7,8,9]).