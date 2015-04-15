import json
import httplib
import string
import base64

from django.http import HttpResponse
from django.views.generic import ListView, TemplateView
from django.core import serializers

from .models import Repo, Contributor

# Removing unwanted chars to slug
def create_slug(text):
    slug_string = ''
    for i in text:
        if i in string.ascii_letters:
            slug_string += i
    return slug_string

def getThingsFromGithubAPI():
    connection = httplib.HTTPSConnection('api.github.com')

    username = 'jherrlin'
    password = 'Gjoher(#2717)'

    auth = base64.encodestring('%s:%s' % (username, password)).replace('\n', '')

    url = '/repos/jherrlin/timeline-project'

    header = {'Content-type':'application/json; charset=UTF-8','Accept':'application/json','User-Agent':'herrlintech.se','Authorization': 'Basic '+str(auth)}
    connection.request('GET', url, {}, header)
    data = json.loads(connection.getresponse().read())

    return data

def getContributors(repo):
    #data = getThingsFromGithubAPI('/repos/videumcodeup/'+repo+'/stats/contributors')
    connection = httplib.HTTPSConnection('api.github.com')

    username = 'jherrlin'
    password = 'Gjoher(#2717)'

    auth = base64.encodestring('%s:%s' % (username, password)).replace('\n', '')

    url = '/repos/jherrlin/timeline-project/stats/contributors'

    header = {'Content-type':'application/json; charset=UTF-8','Accept':'application/json','User-Agent':'herrlintech.se','Authorization': 'Basic '+str(auth)}
    connection.request('GET', url, {}, header)
    data = json.loads(connection.getresponse().read())

    return data


# returns a JSON object
def getReposFromGithubAndSaveIfNotInDatabase():
    repo = getThingsFromGithubAPI()

    # If repo not in database, add it and its contributers
    if repo['id'] not in [j.github_id for j in Repo.objects.all()]:
        new_repo = Repo(
            github_id = repo['id'],
            name = repo['name'],
            description = repo['description'],
            html_url = repo['svn_url'],
            watchers = repo['watchers'],
            language = repo['language'],
            open_issues = repo['open_issues_count'],
            repo_stars = repo['stargazers_count'],

        )
        new_repo.save()
        print('Repo saved')

        # Get contributors from repo
        contributers = getContributors(repo['name'])

        for k in contributers:
            if k['author']['id'] not in [j.github_id for j in Contributor.objects.all()]:
                new_contributor = Contributor(
                    name = k['author']['login'],
                    github_id = k['author']['id'],
                    slug = k['author']['login'],
                    html_url = k['author']['html_url'],
                    avatar_url = k['author']['avatar_url'],
                )

                new_contributor.save()
                new_contributor.repos.add(new_repo)

    return True


def github_hook(request):
    if getReposFromGithubAndSaveIfNotInDatabase() == True:
          return HttpResponse(json.dumps({'status':'ok'}), content_type='application/json')
    else:
        return HttpResponse(json.dumps({'status':'fail'}), content_type='application/json')

def allReposAndContributersJSON(request):

    repos_list = []
    contributers_list = []

    for repo in Repo.objects.all():
        repo = {
            'id' : repo.id,
            'name' : repo.name,
            'description' : repo.description,
            'html_url' : repo.html_url,
            'watchers' : repo.watchers,
            'language' : repo.language
        }
        repos_list.append(repo)

    for con in Contributor.objects.all():
        contributer = {
            'name' : con.name,
            'html_url' : con.html_url,
            'avatar_url' : con.avatar_url,
            'repos' : [r.id for r in con.repos.all()]
        }
        contributers_list.append(contributer)

    data = {
        'repos':repos_list,
        'contributers':contributers_list
    }

    return HttpResponse(json.dumps(data), content_type='application/json')