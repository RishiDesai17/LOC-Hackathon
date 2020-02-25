from rest_framework.serializers import HyperlinkedModelSerializer
from rest_framework import  serializers
from django.contrib.auth.models import User
from . models import Crime,FIR

class NewCrimeserializer(serializers.ModelSerializer):
    class Meta:
        model=Crime
        fields=['Name','Description','Criminal_ID']


class FIRserializer(serializers.ModelSerializer):
    class Meta:
        model=FIR
        fields=['id','Victim_Name','Complaint','Aaadhar_No','Contact_No','email','Chowki']


class FIRserializer1(serializers.ModelSerializer):
    class Meta:
        model=FIR
        fields=['id','Victim_Name','Complaint','Aaadhar_No','Contact_No','email','Chowki','Police_Verified']

