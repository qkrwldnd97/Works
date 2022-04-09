#!/usr/bin/env python
# coding: utf-8

# In[1]:


import pandas as pd
import numpy as np


# In[2]:


data_file = pd.read_csv('CARD_SPENDING_FOREIGNER.txt',sep='\t', engine='python', encoding='CP949')


# In[3]:


print(data_file)


# In[51]:


print (np.average(data_file[data_file['GU_CD'] == 260]['USE_CNT']))


# In[68]:


print (np.nanmean(data_file[data_file['GU_CD'] == 260]['USE_AMT']))


# In[69]:


print (np.nanmean(data_file[data_file['STD_DD'] < 20200120]['USE_CNT']))


# In[70]:


print (np.nanmean(data_file[data_file['STD_DD'] < 20200120]['USE_AMT']))


# In[71]:


print (np.nanmean(data_file[data_file['STD_DD'] >= 20200120]['USE_CNT']))


# In[72]:


print (np.nanmean(data_file[data_file['STD_DD'] >= 20200120]['USE_AMT']))


# In[73]:


aaa = pd.read_csv('CARD_SPENDING_RESIDENT.txt', sep='\t', engine = 'python', encoding = 'CP949')


# In[74]:


print (aaa)


# In[76]:


print (np.nanmean(aaa[aaa['STD_DD'] < 20200120]['USE_CNT']))


# In[77]:


print (np.nanmean(aaa[aaa['STD_DD'] < 20200120]['USE_AMT']))


# In[78]:


print (np.nanmean(aaa[aaa['STD_DD'] > 20200120]['USE_CNT']))


# In[79]:


print (np.nanmean(aaa[aaa['STD_DD'] > 20200120]['USE_AMT']))


# In[83]:


print (np.nanmean(aaa[aaa['SEX_CD'] == "F"]['USE_CNT']))


# In[84]:


print (np.nanmean(aaa[aaa['SEX_CD'] == "F"]['USE_AMT']))


# In[85]:


print (np.nanmean(aaa[aaa['SEX_CD'] == "M"]['USE_CNT']))


# In[86]:


print (np.nanmean(aaa[aaa['SEX_CD'] == "M"]['USE_AMT']))


# In[88]:


print (np.nanmean(aaa[aaa['MCT_CAT_CD'] == 10]['USE_CNT']))


# In[89]:


print (np.nanmean(aaa[aaa['MCT_CAT_CD'] == 10]['USE_AMT']))


# In[ ]:




