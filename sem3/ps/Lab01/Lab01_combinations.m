% 2. Folosind functia nchoosek, sa se afiseze toate perechile (neordonate) de cifre din vectorul
% v=[2 3 5 7]

function Lab01_combinations(in)
    for i=1:length(in)
        nchoosek(in,i)
    end
end