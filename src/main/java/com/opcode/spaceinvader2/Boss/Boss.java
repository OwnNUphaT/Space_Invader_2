package com.opcode.spaceinvader2.Boss;


import com.opcode.spaceinvader2.Enemy.EnemyBullet;
import com.opcode.spaceinvader2.Launcher;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.Objects;
import java.util.Random;


public class Boss extends Pane {
    static final Random rand = new Random();
    private int direction = rand.nextInt(2) == 0 ? -1 : 1;  // -1 for left, 1 for right
    private double gameWidth;
    private double speed = 1;  // Adjust this value based on how fast you want the boss to move
    private double moveDistance = speed * direction;
    private double verticalSpeed = 0; // Adjust as needed
    private ImageView shipImageView;
    private Rectangle hitbox;

    public Boss(double gameWidth) {
        this.gameWidth = gameWidth;
        // Load the image for the player's ship
        Image shipImage = new Image(Objects.requireNonNull(Launcher.class.getResource("/com/opcode/spaceinvader2/image/Boss.png")).toExternalForm());
        shipImageView = new ImageView(shipImage);

        getChildren().addAll(shipImageView);
        // Set initial position
        setTranslateX(245);
        setTranslateY(200);
        System.out.println("Initial Ship Position X: " + getX() + " Y: " + getY());

        // Center the hitbox to the shipImageView
        double hitboxX = getTranslateX();
        double hitboxY = getTranslateY();
        System.out.println("Initial Hitbox Position X: " + hitboxX + " Y: " + hitboxY);

        hitbox = new Rectangle(hitboxX, hitboxY, shipImageView.getFitWidth(), shipImageView.getFitHeight()/ 2);


    }

    public ImageView getShipImageView() {
        return shipImageView;
    }

    public double getX() {
        return getShipImageView().getX();
    }

    public double getY() {
        return getShipImageView().getY();
    }

    public void move() {
        double newX = getX() + direction * moveDistance;

        // Check boundaries and change direction if reached the edges
        if (newX < 0 || newX > gameWidth - getShipImageView().getBoundsInLocal().getWidth()) {
            direction = -direction;
            newX = getX() + direction * moveDistance;
            double newY = getShipImageView().getY() + verticalSpeed;
            getShipImageView().setY(newY);
        }

        getShipImageView().setX(newX);

        hitbox.setX(newX);
        hitbox.setY(getShipImageView().getY());
    }

    public Rectangle getBossHitBox() {
        return hitbox;
    }

    public EnemyBullet shoot() {
        EnemyBullet enemyBullet = new EnemyBullet(getX() + getShipImageView().getFitWidth() / 2 + 10, getY() + shipImageView.getFitHeight());
        return enemyBullet;
    }

    public boolean decideToShoot() {
        return rand.nextInt(1000) < 5;
    }
}
