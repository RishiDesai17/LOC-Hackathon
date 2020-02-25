from django.db import models
from django.contrib.auth.models import User
from django.contrib.auth.models import User
# Create your models here.
class Police(models.Model):
    Ref=models.ForeignKey(User,on_delete=models.CASCADE)
    Name=models.CharField(max_length=100)
    Photo=models.ImageField(upload_to='media')
    Chowki=models.CharField(max_length=100)


class  Crime(models.Model):
    Name=models.CharField(max_length=100)
    
    Description=models.TextField()
    Criminal_ID=models.CharField(max_length=10)



class FIR(models.Model):
    Victim_Name=models.CharField(max_length=100)
    Complaint=models.TextField()
    Aaadhar_No=models.IntegerField()
    Contact_No=models.IntegerField()
    email=models.CharField(max_length=100)
    Chowki=models.TextField()
    User_Verified=models.BooleanField(default=False)
    Police_Verified=models.BooleanField(default=False)


    




  