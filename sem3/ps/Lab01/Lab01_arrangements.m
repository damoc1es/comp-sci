% 3. Scrieti o functie care afiseaza toate aranjamentele de n luate cate k (n,k \in N*, n>=k) ale unui vector cu n elemente

function out=Lab01_arrangements(in, k)
    out = [];
    c=nchoosek(in,k);
    for i=1:nchoosek(length(in), k)
        out=[out ; perms(c(i, :))];
    end
end