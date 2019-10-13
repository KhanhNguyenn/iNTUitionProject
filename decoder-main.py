# -*- coding: utf-8 -*-
"""
Created on Wed Sep 25 11:23:44 2019
@author: DELL
"""
import numpy as np
import sys
from PIL import Image
import random

random.seed(0)
keyList=[]
for k in range(100):
    a=[]
    while len(a)<128:
        x = random.randint(0,256)
        if x not in a:
            a.append(x)
    keyList.append(a)

class decoder(object):
    def __init__(self):
        pass

    def decode(self,address,key,path):#path interm D:\whatever
    #address: path image
    #path: text path to store
        c = 0
        im=Image.open(address)
        na=np.array(im).flatten()

        textList=[]
        for x in na:
           try:
               textList.append(chr(keyList[key%100].index(x)))

           except:
               textList.append('')
               c += 1
        with open(path, 'w') as f:
            for item in textList:
                f.write(item)
address= sys.argv[1]
key= (int)(sys.argv[2])
path= sys.argv[3]
whatever=decoder()
whatever.decode(address,key,path)
