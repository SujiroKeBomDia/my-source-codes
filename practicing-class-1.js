 class Enemy {
    constructor(name, hp) {
        this.name = name
        this.hp = hp
    } attack(dmg) {
        console.log(`${this.name} did an attack of ${dmg} damage!`)
     } receiveDamage(dmg) {
        this.hp = this.hp - dmg
         if (this.hp <= 0) {
             console.log(`${this.name} died!`)
         } else {
             console.log(`${this.name} received ${dmg} damage! \nLife remaining: ${this.hp} HP`)
         }
     }
 }

 class Zombie extends Enemy{
    constructor(name, hp, isPoisonous) {
        super(name, hp)
        this.isPoisonous = isPoisonous
    } attack(dmg) {
        if (this.isPoisonous) {
            console.log(`Zombie ${this.name} attacked and caused ${dmg + 5} damage! (5 extra dmg from poison)`)
        } else {
            console.log(`Zombie ${this.name} attacked and caused ${dmg}`)
        }
     }
 }

 class Skeleton extends Enemy {
     constructor(name, hp, arrows) {
         super(name, hp)
         this.arrows = arrows
     }
 }

 const zumbido_venenoso = new Zombie("poisonous_zombie", 30, true)
 const esqueleto = new Skeleton("skeleton", 20, 99999999999999)

 console.log(`Zombie name: ${zumbido_venenoso.name} \nis it poisonous? ${zumbido_venenoso.isPoisonous}\n`)
 console.log(`Skeleton name: ${esqueleto.name }\nArrows: ${esqueleto.arrows}`)