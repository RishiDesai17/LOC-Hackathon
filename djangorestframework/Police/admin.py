from django.contrib import admin
from . models import Police,Crime,FIR
# Register your models here.
admin.site.register(Police)
admin.site.register(Crime)
admin.site.register(FIR)