
from rest_framework.serializers import HyperlinkedModelSerializer
from rest_framework import  serializers
from django.contrib.auth.models import User


class Signupserializer(serializers.Serializer):
    password1=serializers.CharField(max_length=100)
    class Meta:
        model=User
        fields=['username','email','first_name','last_name','password1']

class LoginSerilaizer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields=['username','password']
