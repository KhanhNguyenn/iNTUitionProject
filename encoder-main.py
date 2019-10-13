# -*- coding: utf-8 -*-
"""
Created on Tue Sep 24 21:57:38 2019
@author: DELL
"""
import numpy as np
import math as m
from PIL import Image
import sys
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

fileName=sys.argv[1]
key= (int)(sys.argv[2])
imageName= sys.argv[3]

lineList = [x for y in open(fileName) for x in y]
ln=len(lineList)
for i in range(3*(1+ln//3)-ln):
    lineList.append(" ");
colorList = []
for x in lineList:
    colorList.append(keyList[key%100][ord(x)])

length=int(len(colorList)/3)
n=int(m.sqrt(length))+1
for i in range(n**2-length):
    for _ in range(3):
        colorList.append(0)
na = np.array(colorList).reshape((n,n,3))

result = Image.fromarray(na.astype(np.uint8))
#resized = cv2.resize(result, (640, 480), interpolation = cv2.INTER_NEAREST)
print("Execute successfully")
result.save("C:\\Users\\KHANH NGUYEN\\Desktop\\eclipse-workspace\\iNTUitionProject\\encrypted-image\\"+imageName+".png","PNG")
