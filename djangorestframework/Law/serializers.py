from rest_framework.serializers import HyperlinkedModelSerializer
from rest_framework import  serializers
from django.contrib.auth.models import User
from . models import Law


class Lawserializer(serializers.ModelSerializer):
    class Meta:
        model=Law
        fields=['chapterNo','Section_No','Description']
