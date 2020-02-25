from django.db import models

# Create your models here.
class Law(models.Model):
    chapterNo=models.CharField(max_length=100)
    Section_No=models.CharField(max_length=10)
    Description=models.TextField()



    