/* eslint-disable no-invalid-this */
const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const dayjs = require('dayjs');
const jwt = require('jwt-simple');
const {
    env, jwtSecret, jwtExpirationInterval,
} = require('../../../config');

/**
 * User Schema
 * @private
 */

const userSchema = new mongoose.Schema({
    created_at: {
        default: Date.now,
        type: Number,
    },
    email: { type: String },
    first_name: { type: String },
    last_name: { type: String },
    password: { type: String },
    sessions: [
        {
            access_token: { type: String },
            client_type: { type: String },
            created_at: {
                default: dayjs().valueOf(),
                type: Number,
            },
            device_token: { type: String },
            is_active: {
                default: true,
                type: String,
            },
            refresh_token: { type: String },
            socket_id: { type: String },
        },
    ],
    updated_at: {
        default: Date.now,
        type: Number,
    },
    verify_tokens: {
        email: {
            default: '',
            type: String,
        },
        reset_password: {
            default: '',
            type: String,
        },
    },
});

/**
 * Add your
 * - pre-save hooks
 * - validations
 * - virtuals
 */
userSchema.pre('save', async function save(next) {
    try {
        if (!this.isModified('password')) return next();

        const rounds = env === 'test' ? 1 : 10;

        const hash = await bcrypt.hash(this.password, rounds);

        this.password = hash;

        return next();
    } catch (error) {
        return next(error);
    }
});

/**
 * Methods
 */
userSchema.method({
    async passwordMatches(password) {
        const result = await bcrypt.compare(password, this.password);

        return result;
    },
    token() {
        const date = dayjs();
        const payload = {
            _id: this._id,
            exp: date.add(7, 'day').valueOf(),
            iat: date.valueOf(),
        };

        return jwt.encode(payload, jwtSecret);
    },
});

/**
 * Statics
 */
userSchema.statics = {};

/**
 * @typedef User
 */

const model = mongoose.model('User', userSchema);


module.exports = model;
