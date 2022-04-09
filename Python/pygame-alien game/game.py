import pygame
import random
import math
#import pdb

pygame.init()
#pdb.set_trace()
screen = pygame.display.set_mode((800,600))

running = True
pygame.display.set_caption("Space invaders")
icon = pygame.image.load('abc.png')
enemy_icon= pygame.image.load('eric.png')
enemy2_icon = pygame.image.load('alien.png')
enemy3_icon = pygame.image.load('alien2.png')
pygame.display.set_icon(icon)
background = pygame.image.load('bg.jpg')
bul = pygame.image.load('bullet.png')

playerX = 370
playerY = 480
enemyX = random.randint(0,800)
enemyY = random.randint(0,600)
enemy2X = random.randint(0,800)
enemy2Y = random.randint(0,600)
enemy3X = random.randint(0,800)
enemy3Y = random.randint(0,600)
bulletX = 0
bulletY = 480
bulletX_change = 0
bulletY_change = 0.9
x_change = 0
y_change = 0
ex_change = 0.3
ey_change = 0.3
ex_change2 = 0.4
ey_change2 = 0.4
ex_change3 = 0.5
ey_change3 = 0.5

score = 0
bullet_state = "ready" #ready invisible & fire currently shooting

def player(x,y):
    screen.blit(icon, (x, y))

def enemy(x,y, icon):
    screen.blit(icon, (x, y))

def bullet(x,y):
    global bullet_state
    bullet_state = "fire"
    screen.blit(bul, (x+25,y-10))

def isCollision(enemyX, enemyY, bulletX, bulletY):
    distance = math.sqrt(math.pow(enemyX-bulletX,2) + math.pow(enemyY - bulletY,2))
    if distance < 27:
        return True
    else:
        return False

while running:
    screen.fill((0,0,0))
    screen.blit(background, (0,0))
    
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False
            pygame.quit()
            break
            
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_LEFT:
                x_change = -0.6
            if event.key == pygame.K_RIGHT:
                x_change = 0.6
            if event.key == pygame.K_UP:
                y_change = -0.6
            if event.key == pygame.K_DOWN:
                y_change = +0.6
            if event.key == pygame.K_SPACE and bullet_state is "ready":
                bulletX = playerX
                bulletY = playerY
                bullet(bulletX, playerY)
        if event.type == pygame.KEYUP:
            if event.key == pygame.K_LEFT or event.key == pygame.K_RIGHT:
                x_change = 0
            elif event.key == pygame.K_UP or event.key == pygame.K_DOWN:
               y_change = 0
            
        
    playerX += x_change
    playerY += y_change
    enemyX += ex_change
    enemyY += ey_change
    enemy2X += ex_change2
    enemy2Y += ey_change2
    enemy3X += ex_change3
    enemy3Y += ey_change3
    

    if playerX < 0:
        playerX = 0
    if playerY < 0:
        playerY = 0
    if playerX >= 736:
        playerX = 736
    if playerY >= 530:
        playerY = 530

    if enemyX <= 0:
        ex_change = 0.25
    if enemyY <= 0:
        ey_change = 0.25
    if enemyX >= 736:
        ex_change = -0.25
    if enemyY >= 530:
        ey_change = -0.25

    if enemy2X <= 0:
        ex_change2 = 0.4
    if enemy2Y <= 0:
        ey_change2 = 0.4
    if enemy2X >= 736:
        ex_change2 = -0.4
    if enemy2Y >= 530:
        ey_change2 = -0.4

    if enemy3X <= 0:
        ex_change3 = 0.6
    if enemy3Y <= 0:
        ey_change3 = 0.6
    if enemy3X >= 736:
        ex_change3 = -0.6
    if enemy3Y >= 530:
        ey_change3 = -0.6

    if bulletY <= 0:
        bulletY = playerY
        bulletX = playerX
        bullet_state = "ready"

    if bullet_state is "fire":
        bullet(bulletX, bulletY)
        bulletY -= bulletY_change

    if isCollision(enemyX, enemyY, bulletX, bulletY):
        bulletY = playerY
        bullet_state = "ready"
        score += 1
        print('Score: {}'.format(score))
        enemyX = random.randint(0,800)
        enemyY = random.randint(0,600)
    elif isCollision(enemy2X, enemy2Y, bulletX, bulletY):
        bulletY = playerY
        bullet_state = "ready"
        score += 1
        print('Score: {}'.format(score))
        enemy2X = random.randint(0,800)
        enemy2Y = random.randint(0,600)
    elif isCollision(enemy3X, enemy3Y, bulletX, bulletY):
        bulletY = playerY
        bullet_state = "ready"
        score += 1
        print('Score: {}'.format(score))
        enemy3X = random.randint(0,800)
        enemy3Y = random.randint(0,600)

    elif isCollision(enemy3X, enemy3Y, playerX, playerY):
        print('Your highest score is {}'.format(score))
        pygame.quit()
        break

    elif isCollision(enemy2X, enemy2Y, playerX, playerY):
        print('Your highest score is {}'.format(score))
        pygame.quit()
        break

    elif isCollision(enemyX, enemyY, playerX, playerY):
        print('Your highest score is {}'.format(score))
        pygame.quit()
        break
        
    
    
    player(playerX, playerY)
    enemy(enemyX, enemyY, enemy_icon)
    enemy(enemy2X, enemy2Y,enemy2_icon)
    enemy(enemy3X, enemy3Y, enemy3_icon)
    pygame.display.update()
    
