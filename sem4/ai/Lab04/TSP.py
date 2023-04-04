from random import randint, shuffle


class TSPChromosome:
    def __init__(self, problParam = None):
        self.__problParam = problParam
        v = [i+1 for i in range(problParam['graph']['noNodes'])]
        shuffle(v)
        self.__repres = v
        self.__fitness = 0.0
    
    @property
    def repres(self):
        return self.__repres
    
    @property
    def fitness(self):
        return self.__fitness 
    
    @repres.setter
    def repres(self, l = []):
        self.__repres = l 
    
    @fitness.setter 
    def fitness(self, fit = 0.0):
        self.__fitness = fit 
    
    def crossover(self, c):
        r = randint(0, len(self.__repres) - 1)
        newrepres = []
        for i in range(r):
            newrepres.append(self.__repres[i])
        for i in range(r, len(self.__repres)):
            newrepres.append(c.__repres[i])
        offspring = TSPChromosome(c.__problParam)
        
        if len(set(newrepres)) != len(newrepres):
            missing = []
            multiple = []
            for i in range(1, len(newrepres)+1):
                k = newrepres.count(i)
                if k == 0:
                    missing.append(i)
                elif k != 1:
                    for _ in range(k-1):
                        multiple.append(i)
            
            for x,y in zip(missing, multiple):
                yi = newrepres.index(y)

                newrepres[yi] = x
        if len(set(newrepres)) != len(newrepres):
            print('PLANG')
        
        offspring.repres = newrepres
        return offspring
    
    def mutation(self):
        # pos = randint(0, len(self.__repres) - 1)
        # self.__repres[pos] = generateNewValue(self.__problParam['min'], self.__problParam['max'])
        pos1 = randint(0, len(self.__repres)-1)
        pos2 = randint(0, len(self.__repres)-1)
        self.__repres[pos1], self.__repres[pos2] = self.__repres[pos2], self.__repres[pos1]
        
    def __str__(self):
        return 'Chromo: ' + str(self.__repres) + ' has fit: ' + str(self.__fitness)
    
    def __repr__(self):
        return self.__str__()
    
    def __eq__(self, c):
        return self.__repres == c.__repres and self.__fitness == c.__fitness
