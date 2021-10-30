
public abstract class BattleLoc extends Location {
	protected Obstacle obstacle;
	protected String award;

	BattleLoc(Player player, String name, Obstacle obstacle, String award) {
		super(player);
		this.obstacle = obstacle;
		this.name = name;
		this.award = award;
	}

	public boolean getLocation() {
		// �dev 1
		if ((this.award.equals("Food") && player.getInv().isFood() == true) ||
				(this.award.equals("Water") && player.getInv().isWater() == true) ||
				(this.award.equals("Firewood") && player.getInv().isFirewood() == true)) {
			System.out.println("\n�d�l kazan�lan b�lgeye tekrar giri� yap�lamaz.");
			return true;
		}

		int obsCount = obstacle.count();
		System.out.println("�uan buradas�n�z : " + this.getName());
		System.out.println("Dikkatli ol! Burada " + obsCount + " tane " + obstacle.getName() + " ya��yor !");
		System.out.print("<S>ava� veya <K>a� :");
		String selCase = scan.nextLine();
		selCase = selCase.toUpperCase();
		if (selCase.equals("S")) {
			if (combat(obsCount)) {
				System.out.println(this.getName() + " b�lgesindeki t�m d��manlar� temizlediniz !");
				if (this.award.equals("Food")) {
					System.out.println(this.award + " Kazand�n�z! ");
					player.getInv().setFood(true);
				} else if (this.award.equals("Water")) {
					System.out.println(this.award + " Kazand�n�z! ");
					player.getInv().setWater(true);
				} else if (this.award.equals("Firewood")) {
					System.out.println(this.award + " Kazand�n�z! ");
					player.getInv().setFirewood(true);
				} else if (this.award.equals("")) {
					setAward();
					if (!this.award.equals("")){
						System.out.println(this.award + " Kazand�n�z! ");
					}
				}
				return true;
			}
			
			if(player.getHealthy() <= 0) {
				System.out.println("�ld�n�z !");
				return false;
			}
		
		}
		return true;
	}

	public boolean combat(int obsCount) {
		for (int i = 0; i < obsCount; i++) {
			int defObsHealth = obstacle.getHealth();
			playerStats();
			enemyStats();
			while (player.getHealthy() > 0 && obstacle.getHealth() > 0) {
				System.out.print("<V>ur veya <K>a� :");
				String selCase = scan.nextLine();
				selCase = selCase.toUpperCase();

				// �dev 2
				if (selCase.equals("V")) {
					int r = (int)(Math.round(Math.random()));
					if(r==0) {
						youHit();
						if (obstacle.getHealth() > 0) {
							obstacleHits();
						}
					} else {
						obstacleHits();
						if (player.getHealthy() > 0) {
							youHit();
						}
					}
				} else {
					return false;
				}
			}

			if (obstacle.getHealth() < player.getHealthy()) {
				System.out.println("D��man� yendiniz !");
				player.setMoney(player.getMoney() + obstacle.getAward());
				System.out.println("G�ncel Paran�z : " + player.getMoney());
				obstacle.setHealth(defObsHealth);
			} else {
				return false;
			}
			System.out.println("-------------------");
		}
		return true;
	}

	public void youHit() {
		System.out.println("Siz vurdunuz !");
		obstacle.setHealth(obstacle.getHealth() - player.getTotalDamage());
		afterHit();
	}

	public void obstacleHits() {

			System.out.println();
			System.out.println("Canavar size vurdu !");
			player.setHealthy(player.getHealthy() - Math.max((obstacle.getDamage() - player.getInv().getArmor()), 0));
			afterHit();

	}

	public void playerStats() {
		System.out.println("Oyuncu De�erleri\n--------------");
		System.out.println("Can:" + player.getHealthy());
		System.out.println("Hasar:" + player.getTotalDamage());
		System.out.println("Para:" + player.getMoney());
		if (player.getInv().getDamage() > 0) {
			System.out.println("Silah:" + player.getInv().getwName());
		}
		if (player.getInv().getArmor() > 0) {
			System.out.println("Z�rh:" + player.getInv().getaName());
		}
	}

	public void enemyStats() {
		System.out.println("\n" + obstacle.getName() + " De�erleri\n--------------");
		System.out.println("Can:" + obstacle.getHealth());
		System.out.println("Hasar:" + obstacle.getDamage());
		System.out.println("�d�l:" + obstacle.getAward());
	}

	public void afterHit() {
		System.out.println("Oyuncu Can�:" + player.getHealthy());
		System.out.println(obstacle.getName() + " Can�:" + obstacle.getHealth());
		System.out.println();
	}

	public void setAward(){
		int r1 = (int)(Math.random()*100);
		int r2 = (int)(Math.random()*100);
		if(r1<15){
			if (r2<20) {
				this.award = "T�fek";
				player.getInv().setDamage(7);
			} else if (r2<50) {
				this.award = "K�l��";
				player.getInv().setDamage(3);
			} else {
				this.award = "Tabanca";
				player.getInv().setDamage(2);
			}
		} else if (r1<30){
			if (r2<20) {
				this.award = "A��r Z�rh";
				player.getInv().setArmor(5);
				player.getInv().setaName(this.award);
			} else if (r2<50) {
				this.award = "Orta Z�rh";
				player.getInv().setArmor(3);
				player.getInv().setaName(this.award);
			} else {
				this.award = "Hafif Z�rh";
				player.getInv().setArmor(1);
				player.getInv().setaName(this.award);
			}
		} else if (r1<55){
			if (r2<20) {
				this.award = "10 Para";
				player.setMoney(player.getMoney()+10);
			} else if (r2<50) {
				this.award = "5 Para";
				player.setMoney(player.getMoney()+5);
			} else {
				this.award = "1 Para";
				player.setMoney(player.getMoney()+1);
			}
		} else {
			System.out.println("Maalesef hi�bir �ey kazanamad�n�z!");
		}
	}

}
