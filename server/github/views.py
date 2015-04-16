import json
import httplib
import string
import base64

from django.http import HttpResponse
from django.views.generic import ListView, TemplateView, View
from django.core import serializers
from django.views.decorators.csrf import csrf_exempt

from .models import Repo, Contributor
from settings.settings import GITHUB_USER, GITHUB_PASSWORD


def getThingsFromGithubAPI():
    connection = httplib.HTTPSConnection('api.github.com')

    username = GITHUB_USER
    password = GITHUB_PASSWORD

    auth = base64.encodestring('%s:%s' % (username, password)).replace('\n', '')

    url = '/repos/jherrlin/timeline-project'

    header = {'Content-type':'application/json; charset=UTF-8','Accept':'application/json','User-Agent':'herrlintech.se','Authorization': 'Basic '+str(auth)}
    connection.request('GET', url, {}, header)
    data = json.loads(connection.getresponse().read())

    return data

def getContributors():

    connection = httplib.HTTPSConnection('api.github.com')

    username = GITHUB_USER
    password = GITHUB_PASSWORD

    auth = base64.encodestring('%s:%s' % (username, password)).replace('\n', '')

    url = '/repos/jherrlin/timeline-project/stats/contributors'

    header = {'Content-type':'application/json; charset=UTF-8','Accept':'application/json','User-Agent':'herrlintech.se','Authorization': 'Basic '+str(auth)}
    connection.request('GET', url, {}, header)
    data = json.loads(connection.getresponse().read())

    return data


# returns a JSON object
def getReposFromGithubAndSaveIfNotInDatabase():
    repo = getThingsFromGithubAPI()
    print(repo)

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
            subscribers_count = repo['subscribers_count'],
            created_at = repo['created_at'],
            pushed_at = repo['pushed_at'],
        )
        new_repo.save()
        print('Repo saved')

        # Get contributors from repo
        contributers = getContributors()

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

    else:
        print("Okey... Updateing repo details and contributers then")
        updateRepoDetails()
        updateContributersOnAllRepos()


    return True

def updateRepoDetails():
    repo = getThingsFromGithubAPI()
    dbRepo = Repo.objects.all()[0]
    print(dbRepo.name)

    dbRepo.github_id = repo['id']
    dbRepo.name = repo['name']
    dbRepo.description = repo['description']
    dbRepo.html_url = repo['svn_url']
    dbRepo.watchers = repo['watchers']
    dbRepo.language = repo['language']
    dbRepo.open_issues = repo['open_issues_count']
    dbRepo.repo_stars = repo['stargazers_count']
    dbRepo.subscribers_count = repo['subscribers_count'],
    dbRepo.created_at = repo['created_at'],
    dbRepo.pushed_at = repo['pushed_at'],
    dbRepo.save()

    print('Repo updated')


def updateContributersOnAllRepos():

    for i in Repo.objects.all():
        contributors = getContributors(i.name)
        for j in contributors:
            #print(j['author']['login']+' contributes to '+i.name)

            # If contributor dont exist in database, add her
            if ( int(j['author']['id']) not in [k.github_id for k in Contributor.objects.all()]):
                    new_contributor = Contributor(
                        name = j['author']['login'],
                        github_id = j['author']['id'],
                        slug = j['author']['login'],
                        html_url = j['author']['html_url'],
                        avatar_url = j['author']['avatar_url'],
                    )

                    new_contributor.save()
                    new_contributor.repos.add(i)

            if ( int(j['author']['id']) in [k.github_id for k in Contributor.objects.all()]):
                contributer = Contributor.objects.get(github_id=j['author']['id'])
                contributer.repos.add(i)

@csrf_exempt
def github_hook(request):
    print("Handling request.....")

    if getReposFromGithubAndSaveIfNotInDatabase() == True:
        return HttpResponse(json.dumps({'status':'ok'}), content_type='application/json')
    else:
        return HttpResponse(json.dumps({'status':'fail'}), content_type='application/json')
